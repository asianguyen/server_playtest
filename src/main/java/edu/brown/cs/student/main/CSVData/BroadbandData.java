package edu.brown.cs.student.main.CSVData;
/**
 * A record that represents the ACS data returned by a broadband query.
 *
 * @param name the name of the state and county
 * @param percentage the percentage of broadband in the area
 * @param state the state
 * @param county the county
 */
public record BroadbandData(
    String name, String percentage, String state, String county, String time) {}
