package edu.brown.cs.student;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/** This class tests Searcher by comparing the terminal's output to the manual string */
public class SearcherTest {

  private final PrintStream standardOut = System.out;
  private final PrintStream standardErr = System.err;

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errorOutputStream = new ByteArrayOutputStream();

  /**
   * This method is called before every test to redirect standard output to a PrintStream called
   * outputStream
   */
  @BeforeEach
  public void setup() {
    System.setOut(new PrintStream(this.outputStream));
    System.setErr(new PrintStream(this.errorOutputStream));
  }

  /** This method is called after every test to restore standard output */
  @AfterEach
  public void after() {
    System.setOut(this.standardOut);
    System.setErr(this.standardErr);
  }

  /**
   * Tests data with column headers
   *
   * @throws IOException
   * @throws FactoryFailureException
   */
  // @Test
  // public void simpleTestWithHeader() throws IOException, FactoryFailureException {
  //   File file =
  //       new File(
  //
  // "/Users/asianguyen/Desktop/CS32/csv-asianguyen/data/census/dol_ri_earnings_disparity.csv");
  //   new Utility(file, "Data Type", "Asian-Pacific Islander");
  //   Assert.assertEquals(
  //       "[[RI, Asian-Pacific Islander, \" $1,080.09 \", 18956.71657,  $1.02 , 4%]]",
  //       this.outputStream.toString().trim());
  // }

  // /**
  //  * Tests data without column headers
  //  *
  //  * @throws IOException
  //  * @throws FactoryFailureException
  //  */
  // @Test
  // public void simpleTestWithoutHeader() throws IOException, FactoryFailureException {
  //   File file =
  //       new File(
  //
  // "/Users/asianguyen/Desktop/CS32/csv-asianguyen/data/census/dol_ri_earnings_disparity.csv");
  //   new Utility(file, "RI");
  //   Assert.assertEquals(
  //       "[[RI, White, \" $1,058.47 \", 395773.6521,  $1.00 , 75%], "
  //           + "[RI, Black,  $770.26 , 30424.80376,  $0.73 , 6%], "
  //           + "[RI, Native American/American Indian,  $471.07 , 2315.505646,  $0.45 , 0%], "
  //           + "[RI, Asian-Pacific Islander, \" $1,080.09 \", 18956.71657,  $1.02 , 4%], "
  //           + "[RI, Hispanic/Latino,  $673.14 , 74596.18851,  $0.64 , 14%], "
  //           + "[RI, Multiracial,  $971.89 , 8883.049171,  $0.92 , 2%]]",
  //       this.outputStream.toString().trim());
  // }

  // /**
  //  * Tests data that the user wants to search by the column count
  //  *
  //  * @throws IOException
  //  * @throws FactoryFailureException
  //  */
  // @Test
  // public void testWithIntegerHeader() throws IOException, FactoryFailureException {
  //   File file =
  //       new File(
  //
  // "/Users/asianguyen/Desktop/CS32/csv-asianguyen/data/census/dol_ri_earnings_disparity.csv");
  //   new Utility(file, "4", " $0.73 ");
  //   Assert.assertEquals(
  //       "[[RI, Black,  $770.26 , 30424.80376,  $0.73 , 6%]]",
  // this.outputStream.toString().trim());
  // }

  // /**
  //  * Tests if the user inputted a column's corresponding integer value but the column number is
  // out
  //  * of scope
  //  *
  //  * @throws IOException
  //  * @throws FactoryFailureException
  //  */
  // @Test
  // public void testWithIntegerHeaderNotIncluded() throws IOException, FactoryFailureException {
  //   File file =
  //       new File(
  //           "/Users/asianguyen/Desktop/CS32/csv-asianguyen/data/malformed/malformed_signs.csv");
  //   new Utility(file, "2", "Nick");
  //   Assert.assertEquals("[[Gemini, Roberto, Nick]]", this.outputStream.toString().trim());
  //   Assert.assertEquals(
  //       "Column specified by integer is not included in data\n"
  //           + "Row 3 has excess values\n"
  //           + "Row 6 is missing a value\n"
  //           + "Row 7 is missing a value\n"
  //           + "Row 11 is missing a value",
  //       this.errorOutputStream.toString().trim());
  // }

  // /**
  //  * Tests data when the user wants to search for a certain value, but it is in the wrong column
  //  * that the user specified
  //  *
  //  * @throws IOException
  //  * @throws FactoryFailureException
  //  */
  // @Test
  // public void testWithValueInWrongCol() throws IOException, FactoryFailureException {
  //   File file =
  //       new File(
  //           "/Users/asianguyen/Desktop/CS32/csv-asianguyen/data/malformed/malformed_signs.csv");
  //   new Utility(file, "Member", "Nick");
  //   Assert.assertEquals(this.outputStream.toString().trim(), "[]");
  //   Assert.assertEquals(
  //       this.errorOutputStream.toString().trim(),
  //       "Row 3 has excess values\n"
  //           + "Row 6 is missing a value\n"
  //           + "Row 7 is missing a value\n"
  //           + "Row 11 is missing a value\n"
  //           + "Could not find value within specified column");
  // }

  // /**
  //  * Tests data with malformed rows and missing values
  //  *
  //  * @throws IOException
  //  * @throws FactoryFailureException
  //  */
  // @Test
  // public void testWithExcessValuesAndMissingValues() throws IOException, FactoryFailureException
  // {
  //   File file =
  //       new File(
  //           "/Users/asianguyen/Desktop/CS32/csv-asianguyen/data/malformed/malformed_signs.csv");
  //   new Utility(file, "Member", "Nicole");
  //   Assert.assertEquals("[[Scorpio, Nicole]]", this.outputStream.toString().trim());
  //   Assert.assertEquals(
  //       "Row 3 has excess values\n"
  //           + "Row 6 is missing a value\n"
  //           + "Row 7 is missing a value\n"
  //           + "Row 11 is missing a value",
  //       this.errorOutputStream.toString().trim());
  // }

  // /**
  //  * Tests when the file path is not in the data directory
  //  *
  //  * @throws IOException
  //  * @throws FactoryFailureException
  //  */
  // @Test
  // public void testInWrongDirectory() throws IOException, FactoryFailureException {
  //   File file = new File("census/postsecondary_education.csv");
  //   new Utility(file, "Member", "Nicole");
  //   Assert.assertEquals(
  //       "Unable to use file: file either does not exist or is not in the data directory",
  //       this.errorOutputStream.toString().trim());
  // }

  // /**
  //  * Tests that searcher will still parse correctly even if there is white space surrounding a
  // value
  //  * in the CSV file
  //  *
  //  * @throws IOException
  //  * @throws FactoryFailureException
  //  */
  // @Test
  // public void testWithWhiteSpace() throws IOException, FactoryFailureException {
  //   File file = new File("data/census/dol_ri_earnings_disparity.csv");
  //   new Utility(file, "$673.14");
  //   Assert.assertEquals(
  //       "[[RI, Hispanic/Latino,  $673.14 , 74596.18851,  $0.64 , 14%]]",
  //       this.outputStream.toString().trim());
  // }

  // /**
  //  * Tests that the searcher will return an empty list if the value is not found
  //  *
  //  * @throws IOException
  //  * @throws FactoryFailureException
  //  */
  // @Test
  // public void testUnfoundValueByComparingList() throws IOException, FactoryFailureException {
  //   File file = new File("data/census/postsecondary_education.csv");
  //   ArrayListCreator creatorFromRow = new ArrayListCreator();
  //   FileReader fileReader = new FileReader(file);
  //   Parser<ArrayList<String>> parser = new Parser<>(fileReader, creatorFromRow);
  //   Searcher searcher = new Searcher(parser, true);
  //   ArrayList<ArrayList<String>> list = new ArrayList<>();
  //   Assert.assertEquals(searcher.search("Harvard University", "University"), list);
  // }

  // /**
  //  * Tests when the value cannot be found
  //  *
  //  * @throws IOException
  //  * @throws FactoryFailureException
  //  */
  // @Test
  // public void testUnfoundValue() throws IOException, FactoryFailureException {
  //   File file = new File("data/census/postsecondary_education.csv");
  //   new Utility(file, "Harvard University");
  //   Assert.assertEquals("[]", this.outputStream.toString().trim());
  //   Assert.assertEquals(
  //       "Could not find value within data", this.errorOutputStream.toString().trim());
  // }

  // // what if header started with 5, 6, 7, 8, but user wanted to search 2 column

  // /**
  //  * Tests if the file exists at all
  //  *
  //  * @throws IOException
  //  * @throws FactoryFailureException
  //  */
  // @Test
  // public void testFileValidity() throws IOException, FactoryFailureException {
  //   File file = new File("census/secondary_education.csv");
  //   new Utility(file, "Harvard University");
  //   Assert.assertEquals(
  //       "Unable to use file: file either does not exist or is not in the data directory",
  //       this.errorOutputStream.toString().trim());
  // }
}
