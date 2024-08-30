package edu.brown.cs.student.main.CSVData;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the shared state between load, search, and view. It holds the CSV file's
 * data.
 */
public class CSVData implements CSVDatasource {

  private List<ArrayList<String>> parsedCSV = new ArrayList<>();
  private ArrayList<String> csvHeaders;

  /**
   * A method that gets the current CSV.
   *
   * @return a defensive copy of the parsed CSV
   */
  @Override
  public List<ArrayList<String>> getCurrentCSV() {
    return new ArrayList<>(this.parsedCSV);
  }

  /**
   * A method that sets the parsedCSV variable to the current CSV.
   *
   * @param parsedCSV the parsed CSV
   */
  @Override
  public void setCurrentCSV(List<ArrayList<String>> parsedCSV) {
    this.parsedCSV = parsedCSV;
  }

  /**
   * A method that gets the headers of the CSV.
   *
   * @return a defensive copy of the CSV's headers
   */
  @Override
  public ArrayList<String> getCSVHeaders() {
    return new ArrayList<>(this.csvHeaders);
  }

  /**
   * A method that sets the csvHeaders variable to the current CSV's headers.
   *
   * @param headers the CSV headers
   */
  @Override
  public void setCSVHeaders(ArrayList<String> headers) {
    this.csvHeaders = headers;
  }
}
