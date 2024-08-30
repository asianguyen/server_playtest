package edu.brown.cs.student.main.Handlers;

import edu.brown.cs.student.main.CSVData.CSVDatasource;
import edu.brown.cs.student.main.JSONAdaptors.Serializer;
import edu.brown.cs.student.main.UtilityClasses.Searcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/** A class that handles queries related to searching a CSV file. */
public class SearchHandler implements Route {
  private CSVDatasource state;

  /**
   * The constructor of the SearchHandler class that initializes the shared state.
   *
   * @param state the shared state between load, search, and view
   */
  public SearchHandler(CSVDatasource state) {
    this.state = state;
  }

  /**
   * A method that handles search queries and puts the rows that include the value being searched
   * for into a JSON to be returned to the user.
   *
   * @param request the request made by the user
   * @param response response to be made
   * @return a JSON that holds the data to be shown to the user
   */
  @Override
  public Object handle(Request request, Response response) {
    Map<String, Object> responseMap = new HashMap<>();
    String value = request.queryParams("value");

    // return with error if no value parameter is entered
    if (value == null) {
      responseMap.put("result", "error_bad_request");
      return new Serializer().serialize(responseMap);
    }

    String column = request.queryParams("column");
    // put requested parameters into the response map
    responseMap.put("requested_value", value);
    if (column != null) {
      responseMap.put("requested_column", column);
    } else {
      responseMap.put("requested_column", "none_specified");
    }

    List<ArrayList<String>> csv = this.state.getCurrentCSV();
    if (csv.isEmpty()) { // verifies that the user loaded a CSV
      responseMap.put("result", "error_no_csv_loaded");
    } else {
      boolean hasHeaders = true;
      ArrayList<String> headers =
          this.state.getCSVHeaders(); // if there are headers set the boolean accordingly
      if (headers.isEmpty()) {
        hasHeaders = false;
      }

      Searcher searcher;
      if (hasHeaders) {
        searcher = new Searcher(csv, headers);
      } else {
        searcher = new Searcher(csv);
      }

      if (column == null) { // if the user did not specify a column to search by
        List<ArrayList<String>> foundRows = searcher.search(value, hasHeaders);
        if (foundRows.isEmpty()) {
          responseMap.put("data", "value_not_found");
        } else {
          responseMap.put("result", "success");
          responseMap.put("data", foundRows);
        }
      } else { // search by a specific column
        try {
          List<ArrayList<String>> foundRows = searcher.search(value, column, hasHeaders);
          if (foundRows.isEmpty()) {
            responseMap.put("data", "value_not_found");
          } else {
            responseMap.put("data", foundRows);
          }
          // thrown when an invalid column is entered
        } catch (IllegalArgumentException e) {
          responseMap.put("result", "error_bad_request");
        }
      }
      // add malformed row warnings to the responseMap
      ArrayList<Integer> malformedRows = searcher.getMalformedRows();
      if (!malformedRows.isEmpty()) {
        responseMap.put("malformed_rows", malformedRows);
      }
    }
    return new Serializer().serialize(responseMap);
  }
}
