package edu.brown.cs.student.main.CSVData;

import java.io.IOException;

/** An interface that can get the broadband of a certain state and county. */
public interface BroadbandDatasource {

  /**
   * Retrieve the broadband data in the form of the BroadbandData record at a given state and
   * county.
   *
   * @param state the state to be searched
   * @param county the county to be searched
   * @return the data in the form of the BroadbandData record
   * @throws IOException invalid input/output
   */
  BroadbandData getBroadband(String state, String county) throws IOException;
}
