package edu.brown.cs.student.main.UtilityClasses;

import edu.brown.cs.student.main.FactoryFailureException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Searcher class that handles the searching of a CSV for a word. */
public class Searcher {

  private final List<ArrayList<String>> csv;
  private final ArrayList<String> csvHeaders;
  private final ArrayList<Integer> malformedRows = new ArrayList<>();

  /**
   * Constructor that is used when searching through a specific header.
   *
   * @param parsedCSV a CSV that has already been parsed into an ArrayList<String>.
   * @param csvHeaders a list containing the headers.
   */
  public Searcher(List<ArrayList<String>> parsedCSV, ArrayList<String> csvHeaders) {
    this.csv = parsedCSV;
    this.csvHeaders = csvHeaders;
  }

  /**
   * Constructor that is used when searching all headers.
   *
   * @param parsedCSV a CSV that has already been parsed into an ArrayList<String>
   */
  public Searcher(List<ArrayList<String>> parsedCSV) {
    this.csv = parsedCSV;
    this.csvHeaders = new ArrayList<>();
  }

  /**
   * Method that searches a CSV for the String toFind. This search method has no column specified
   * and searches all the CSV rows.
   *
   * @param toFind - the String to look for.
   * @param hasHeaders - whether the CSV has headers.
   * @return - a List<ArrayList<String>> of rows with the desired word.
   * @throws FactoryFailureException
   * @throws IOException
   */
  public List<ArrayList<String>> search(String toFind, boolean hasHeaders) {
    List<ArrayList<String>> rowsWithVal = new ArrayList<>();
    int numCols;
    // find the number of expected columns based on the number of headers or the
    // number of items in
    // the first row
    if (hasHeaders) {
      numCols = findNumCols(this.csvHeaders);
    } else {
      numCols = findNumCols(this.csv.get(0));
    }

    for (ArrayList<String> row : this.csv) {
      // adds to malformedRows to warn users that their CSV may be malformed, but it
      // is still
      // searchable
      if (numCols != this.findNumCols(row)) {
        this.malformedRows.add(this.csv.indexOf(row));
      }
      for (String word : row) { // check each column
        if (word.toLowerCase().equals(toFind.toLowerCase())) {
          rowsWithVal.add(row);
          break;
        }
      }
    }
    return rowsWithVal;
  }

  /**
   * Method that searches a CSV for the String toFind. This search method has a column specified and
   * searches within that column.
   *
   * @param toFind - the String to look for.
   * @param column - the column to look in.
   * @param hasHeaders - whether the CSV has headers.
   * @return - a List of rows with the desired word.
   * @throws FactoryFailureException error when parsing rows
   * @throws IOException invalid input/output
   */
  public List<ArrayList<String>> search(String toFind, String column, boolean hasHeaders)
      throws IllegalArgumentException {

    List<ArrayList<String>> rowsWithVal = new ArrayList<>();
    // want to keep the headers in to determine the header's column index

    int numCols = 0;
    if (hasHeaders) {
      numCols = findNumCols(this.csvHeaders);
    } else {
      numCols = findNumCols(this.csv.get(0));
    }

    int colIndex = -1;
    // determine String header column index
    for (int i = 0; i < numCols; i++) {
      // for header names
      if (hasHeaders) {
        if (this.csvHeaders.get(i).toLowerCase().equals(column.toLowerCase())) {
          colIndex = i;
          break;
        }
      }
      // for column indexes
      if (Integer.toString(i).equals(column)) {
        colIndex = i;
        break;
      }
    }

    // header was not found or index is invalid
    if (colIndex < 0) {
      throw new IllegalArgumentException("invalid column");
    }

    // start at the second row if there's headers
    for (int i = 0; i < this.csv.size(); i++) { // for each row
      ArrayList<String> row = this.csv.get(i);
      if (numCols != this.findNumCols(row)) {
        this.malformedRows.add(this.csv.indexOf(row));
      }
      try {
        if (row.get(colIndex).toLowerCase().equals(toFind.toLowerCase())) {
          rowsWithVal.add(row);
        }
        // skip the row and continue searching if this value doesn't exist
      } catch (IndexOutOfBoundsException e) {
      }
    }
    return rowsWithVal;
  }

  /**
   * Helper method that determines how many columns are in a row.
   *
   * @param row - the row to count in.
   * @return - the number of columns found.
   */
  public int findNumCols(List<String> row) {
    int numCols = 0;
    for (int i = 0; i < row.size(); i++) {
      numCols++;
    }
    return numCols;
  }

  /**
   * Method that retrieves the malformed rows in the CSV.
   *
   * @return ArrayList of malformed row indexes.
   */
  public ArrayList<Integer> getMalformedRows() {
    return new ArrayList<>(this.malformedRows);
  }
}
