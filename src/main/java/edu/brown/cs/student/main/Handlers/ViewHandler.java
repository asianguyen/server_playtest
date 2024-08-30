package edu.brown.cs.student.main.Handlers;

import edu.brown.cs.student.main.CSVData.CSVDatasource;
import edu.brown.cs.student.main.JSONAdaptors.Serializer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/** A class that handles queries related to viewing a CSV file. */
public class ViewHandler implements Route {
  private final CSVDatasource state;

  /**
   * The constructor of the ViewHandler class that initializes the shared state.
   *
   * @param state the shared state between load, search, and view
   */
  public ViewHandler(CSVDatasource state) {
    this.state = state;
  }

  /**
   * A method that handles view queries and puts the CSV file wanting to be viewed into a JSON to be
   * returned to the user.
   *
   * @param request the request made by the user
   * @param response response to be made
   * @return a JSON that holds the data to be shown to the user
   */
  @Override
  public Object handle(Request request, Response response) {
    Map<String, Object> responseMap = new HashMap<>();
    List<ArrayList<String>> csv = this.state.getCurrentCSV();
    if (csv.isEmpty()) {
      responseMap.put("result", "error_no_csv_loaded");
    } else {
      // add row of headers to the csv if there are headers
      ArrayList<String> headers = this.state.getCSVHeaders();
      if (!headers.isEmpty()) {
        csv.add(0, headers);
      }
      responseMap.put("result", "success");
      responseMap.put("data", csv);
    }
    return new Serializer().serialize(responseMap);
  }
}
