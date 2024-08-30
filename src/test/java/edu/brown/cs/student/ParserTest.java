package edu.brown.cs.student;

import edu.brown.cs.student.main.DeveloperObjectClasses.ArrayListCreator;
import edu.brown.cs.student.main.DeveloperObjectClasses.CreatorFromRow;
import edu.brown.cs.student.main.DeveloperObjectClasses.Person;
import edu.brown.cs.student.main.DeveloperObjectClasses.PersonCreator;
import edu.brown.cs.student.main.FactoryFailureException;
import edu.brown.cs.student.main.UtilityClasses.Parser;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

public class ParserTest {

  /**
   * Tests if parse works with different Reader objects
   *
   * @throws IOException
   * @throws FactoryFailureException
   */
  @Test
  public void testWithDifferentReader() throws IOException, FactoryFailureException {
    CreatorFromRow<ArrayList<String>> creatorFromRow = new ArrayListCreator();
    StringReader reader =
        new StringReader(
            "Star Sign,Member,\n"
                + "Aries,Annie,\n"
                + "Taurus,Albert\n"
                + "Gemini,Roberto,Nick\n"
                + "Cancer,Alexis,\n"
                + "Leo,Gabi,\n"
                + "Virgo,\n"
                + "Libra,,\n"
                + "Scorpio,Nicole,\n"
                + "Sagittarius,Tim,\n"
                + "Capricorn,Sophie,\n"
                + "Aquarius,,\n"
                + "Pisces,Danny,");
    Parser<ArrayList<String>> parser = new Parser<>(reader, creatorFromRow);

    File file = new File("data/malformed/malformed_signs.csv");
    FileReader fileReader = new FileReader(file);
    Parser<ArrayList<String>> parser2 = new Parser<>(fileReader, creatorFromRow);
    Assert.assertEquals(parser2.parse(), parser.parse());
  }

  /**
   * Tests parse with a Person Object
   *
   * @throws IOException
   * @throws FactoryFailureException
   */
  @Test
  public void testWithPersonObject() throws IOException, FactoryFailureException {
    CreatorFromRow<Person> personCreator = new PersonCreator();
    File file = new File("data/stars/person.csv");
    FileReader reader = new FileReader(file);
    Parser<Person> parser = new Parser<>(reader, personCreator);
    List<Person> list = parser.parse();
    Assert.assertEquals(20, list.get(0).getAge());
    Assert.assertEquals("Asia", list.get(0).getName());
    Assert.assertEquals("Student", list.get(0).getJob());
  }

  /**
   * Tests when there are mistakes when converting rows to Person objects
   *
   * @throws IOException
   * @throws FactoryFailureException
   */
  @Test
  public void testWithPersonObjectAndMalformedRows() throws IOException, FactoryFailureException {
    CreatorFromRow<Person> personCreator = new PersonCreator();
    File file = new File("data/malformed/testing.csv");
    FileReader reader = new FileReader(file);
    Parser<Person> parser = new Parser<>(reader, personCreator);
    Assert.assertThrows(FactoryFailureException.class, () -> parser.parse());
  }

  /**
   * Tests the error in the regex by confirming that values with quotation marks will still have
   * quotation marks after parsing
   *
   * @throws IOException
   * @throws FactoryFailureException
   */
  @Test
  public void testRegexQuotations() throws IOException, FactoryFailureException {
    CreatorFromRow<ArrayList<String>> creatorFromRow = new ArrayListCreator();
    File file = new File("data/census/income_by_race.csv");
    FileReader reader = new FileReader(file);
    Parser<ArrayList<String>> parser = new Parser<>(reader, creatorFromRow);
    ArrayList<ArrayList<String>> list = parser.parse();
    Assert.assertEquals(list.get(1).get(6), "\"Bristol County, RI\"");
  }
}
