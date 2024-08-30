package edu.brown.cs.student.main.JSONAdaptors;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import java.util.Map;

/** A class that handles serializing objects into a JSON. */
public class Serializer {

  private final JsonAdapter<Map<String, Object>> adapter;

  /** The constructor of Serializer that sets up the functionality to create a JSON. */
  public Serializer() {
    Moshi moshi = new Moshi.Builder().build();
    Type mapStringObject = Types.newParameterizedType(Map.class, String.class, Object.class);
    this.adapter = moshi.adapter(mapStringObject);
  }

  /**
   * A method that creates a JSON from a map.
   *
   * @param map the map to convert
   * @return a String with the data from map in JSON form
   */
  public String serialize(Map<String, Object> map) {
    return this.adapter.toJson(map);
  }
}
