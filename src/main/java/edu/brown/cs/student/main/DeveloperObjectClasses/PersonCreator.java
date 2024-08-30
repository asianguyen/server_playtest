package edu.brown.cs.student.main.DeveloperObjectClasses;

import edu.brown.cs.student.main.FactoryFailureException;
import java.util.List;

/** A class that implements the CreatorFromRow interface to convert data into Person objects */
public class PersonCreator implements CreatorFromRow<Person> {

  public PersonCreator() {}

  /**
   * The create method declared in the interface. Converts a row into a Person object
   *
   * @param row
   * @return
   * @throws FactoryFailureException
   */
  @Override
  public Person create(List<String> row) throws FactoryFailureException {
    if (row.size()
        != 3) { // throws FactoryFailureException if the amount of values in a row does not match
      // with the fields to create a Person
      throw new FactoryFailureException("Error in converting rows", row);
    } else if (row.get(1).equals("")) { // if the age field is left blank replace it with a -1
      return new Person(row.get(0), -1, row.get(2));
    } else {
      return new Person(row.get(0), Integer.parseInt(row.get(1)), row.get(2));
    }
  }
}
