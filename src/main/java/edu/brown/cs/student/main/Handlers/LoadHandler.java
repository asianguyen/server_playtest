package edu.brown.cs.student.main.Handlers;

import edu.brown.cs.student.main.CSVData.CSVDatasource;
import edu.brown.cs.student.main.DeveloperObjectClasses.ArrayListCreator;
import edu.brown.cs.student.main.FactoryFailureException;
import edu.brown.cs.student.main.JSONAdaptors.Serializer;
import edu.brown.cs.student.main.UtilityClasses.Parser;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/** A class that handles queries related to loading a CSV. */
public class LoadHandler implements Route {

  private final CSVDatasource state;
  private String file;

  /**
   * The constructor of the LoadHandler class that initializes the shared state.
   *
   * @param state the shared state between load, search, and view
   */
  public LoadHandler(CSVDatasource state) {
    this.state = state;
  }

  /**
   * A method that handles load queries and puts the loaded CSV into a JSON to be returned to the
   * user.
   *
   * @param request the request made by the user
   * @param response response to be made
   * @return: a JSON that holds the data to be shown to the user
   */
  @Override
  public Object handle(Request request, Response response) {
    this.file = request.queryParams("file");
    String hasHeaders = request.queryParams("headers");

    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("headers", hasHeaders);

    // if file isn't entered, return with an error
    if (this.file == null) {
      responseMap.put("result", "error_bad_request");
      return new Serializer().serialize(responseMap);
    }
    // if headers isn't specified, default to no headers
    if (hasHeaders == null) {
      hasHeaders = "no";
    }

    try {
      // directly return the error message if the file is invalid
      if (!this.checkValidFile(responseMap)) {
        return new Serializer().serialize(responseMap);
      }

      // parse loaded csv so search and view don't have to parse again
      Parser parser = new Parser<>(new FileReader(this.file), new ArrayListCreator());
      List<ArrayList<String>> parsedCSV = parser.parse();
      boolean headers = this.convertHeaderResponse(hasHeaders);
      // set the shared state's csv and header variables
      if (headers) {
        this.state.setCurrentCSV(parsedCSV.subList(1, parsedCSV.size() - 1));
        this.state.setCSVHeaders(parsedCSV.get(0));
      } else {
        this.state.setCurrentCSV(parsedCSV);
        this.state.setCSVHeaders(new ArrayList<>());
      }
      responseMap.put("result", "success");
      responseMap.put("filepath", this.file);

    } catch (IOException | FactoryFailureException e) {
      responseMap.put("result", "error_datasource");
    } catch (IllegalArgumentException e) {
      responseMap.put("result", "error_bad_request");
    }

    return new Serializer().serialize(responseMap);
  }

  /**
   * A method that converts the user's response to if the file has headers or not into a boolean.
   *
   * @param hasHeaders the response to the headers parameter
   * @return true if hasHeaders false if doesn't
   */
  private boolean convertHeaderResponse(String hasHeaders) {
    if (hasHeaders == null || hasHeaders.isEmpty()) {
      return false;
    }
    if (hasHeaders.toLowerCase().equals("yes")) {
      return true;
    } else if (hasHeaders.toLowerCase().equals("no")) {
      return false;
    } else {
      throw new IllegalArgumentException("invalid header parameter");
    }
  }

  /**
   * Method that checks if the requested file is in the correct data/ directory by adding data/ to
   * the file if it is not already there, then making sure the next characters are not ../
   *
   * @param responseMap The map returned by handle() that holds the error message if the file is
   *     invalid.
   * @return True if file is valid, false if not.
   */
  private boolean checkValidFile(Map<String, Object> responseMap) {
    String rootPath = "data/";
    if (!this.file.substring(0, 5).equals(rootPath)) {
      this.file = rootPath + this.file;
    }
    // ensure user can't navigate up to a parent directory
    if (this.file.substring(5, 8).equals("../")) {
      responseMap.put("result", "error_invalid_file_directory");
      return false;
    }
    return true;
  }
}
