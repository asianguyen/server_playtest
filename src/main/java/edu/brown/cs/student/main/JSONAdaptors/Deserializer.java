package edu.brown.cs.student.main.JSONAdaptors;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/** A class that handles deserializing objects from a JSON to a List. */
public class Deserializer {

  private final JsonAdapter<List<List<String>>> adapter;

  /** The constructor of Deserializer that sets up the functionality to deserialize a JSON. */
  public Deserializer() {
    Moshi moshi = new Moshi.Builder().build();
    Type listStringObject = Types.newParameterizedType(List.class, List.class, String.class);
    this.adapter = moshi.adapter(listStringObject);
  }

  /** A method that creates a List<List<String>> from a JSON. */
  public List<List<String>> deserialize() throws IOException {
    FileReader file =
        new FileReader(
            "/Users/asianguyen/Desktop/Brown/JuniorYear/CS32TA/serverplay/data/mockedJSON.json");
    BufferedReader reader = new BufferedReader(file);
    String responseString = reader.lines().collect(Collectors.joining("\n"));
    List<List<String>> body = adapter.fromJson(responseString);
    System.out.println(body);
    return body;
  }
}
