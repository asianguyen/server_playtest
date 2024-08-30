package edu.brown.cs.student.main.UtilityClasses;

import edu.brown.cs.student.main.DeveloperObjectClasses.CreatorFromRow;
import edu.brown.cs.student.main.FactoryFailureException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class represents a Parser that can parse data into any Object defined by T
 *
 * @param <T> a generic type that describes the object the data will be converted to
 */
public class Parser<T> {

  // Common alternate regex: "(?!\\B\"[^\"]*),(?![^\"]*\"\\B)"
  static final Pattern regexSplitCSVRow =
      Pattern.compile(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*(?![^\\\"]*\\\"))");
  private BufferedReader reader;
  private CreatorFromRow<T> creatorFromRow;

  /**
   * Parser's constructor which wraps the Reader into a BufferedReader and sets the value of the
   * class that implements CreatorFromRow
   *
   * @param reader Any Reader object
   * @param creatorFromRow Any class that implements the CreatorFromRow interface
   */
  public Parser(Reader reader, CreatorFromRow<T> creatorFromRow) {
    this.reader = new BufferedReader(reader);
    this.creatorFromRow = creatorFromRow;
  }

  /**
   * This method parses lines, splits them up according to the regex, converts the rows into the T
   * type defined by the developer, and returns an ArrayList of that type.
   *
   * @return
   * @throws IOException
   * @throws FactoryFailureException
   */
  public ArrayList<T> parse() throws IOException, FactoryFailureException {
    ArrayList<T> arrayList = new ArrayList<>();
    String row;
    while ((row = this.reader.readLine()) != null) {
      String[] result = regexSplitCSVRow.split(row);
      arrayList.add(this.creatorFromRow.create(List.of(result)));
    }
    this.reader.close();
    return arrayList;
  }
}
