package edu.brown.cs.student.main.DeveloperObjectClasses;

import java.util.ArrayList;
import java.util.List;

/** This is a class that creates each row of the CSV file into an ArrayList of Strings */
public class ArrayListCreator implements CreatorFromRow<ArrayList<String>> {

  public ArrayListCreator() {}

  /**
   * This is the overriden create method from the interface this class implemented. It returns the
   * row of the CSV file as an ArrayList.
   *
   * @param row
   * @return
   */
  @Override
  public ArrayList<String> create(List<String> row) {
    return new ArrayList<>(row);
  }
}
