package edu.brown.cs.student.server;

import com.squareup.moshi.Moshi;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * When the API receives a GET request for a non-implemented endpoint, give out "error_bad_json".
 */
public class DefaultHandler implements Route {
  private final Map<String, Object> responseMap = new HashMap<>();

  /**
   * Processes the non-supported GET request.
   *
   * @param request an HTTP request
   * @param response an HTTP response
   * @return a JSON adapted Map of "result" to "error_bad_json"
   * @throws Exception if an error occurs
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    Moshi moshi = new Moshi.Builder().build();
    this.responseMap.put("result", "error_bad_json");
    return moshi.adapter(Map.class).toJson(this.responseMap);
  }
}
