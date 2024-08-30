package edu.brown.cs.student.main.CSVData;

import edu.brown.cs.student.main.JSONAdaptors.Deserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MockedBroadband implements BroadbandDatasource {

  public MockedBroadband() {}

  private BroadbandData getBroadBandPercentage(String state, String county) throws IOException {
    Deserializer deserializer = new Deserializer();
    List<List<String>> data = deserializer.deserialize();

    return new BroadbandData(
        data.get(1).get(0), data.get(1).get(1), data.get(1).get(2), data.get(1).get(3), getTime());
  }

  private static String getTime() {
    LocalDateTime dateAndTime = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return dateAndTime.format(format);
  }

  @Override
  public BroadbandData getBroadband(String state, String county) throws IOException {
    return getBroadBandPercentage(state, county);
  }
}
