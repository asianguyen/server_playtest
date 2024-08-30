package edu.brown.cs.student.main.CSVData;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface that declares the methods needed to share the CSV file's data between the load,
 * search, and view handlers.
 */
public interface CSVDatasource {

  /**
   * Gets the current CSV.
   *
   * @return the current CSV
   */
  List<ArrayList<String>> getCurrentCSV();

  /**
   * Sets the current CSV.
   *
   * @param parsedCSV the parsed CSV
   */
  void setCurrentCSV(List<ArrayList<String>> parsedCSV);

  /**
   * Gets the CSV headers.
   *
   * @return returns the headers
   */
  ArrayList<String> getCSVHeaders();

  /**
   * Sets the CSV headers.
   *
   * @param headers headers of the CSV
   */
  void setCSVHeaders(ArrayList<String> headers);
}
