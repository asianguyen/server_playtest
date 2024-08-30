# Project Details
#### Project Name:
- Sprint 1: CSV
#### Team members and contributions:
- CSV was an individual project
#### Total time:
- 25 hours
#### Link to repo:
- link to repo: https://github.com/cs0320-s24/csv-asianguyen
# Design Choices
## Classes and Interfaces:
### UtilityClasses
  I first have the Parser and Searcher classes that have the functionality of parsing and
  searching, respectively.
##### Parser: 
Parser is generic as it is able to take in any type T to parse. It
  reads line by line from the CSV file through a BufferedReader and then splits the line based
  on the regex. It then uses a strategy pattern by calling the create method declared in the
  interface, so when developers decide to define what Object they are converting the rows to,
  they just need to implement the CreatorFromRow interface. Finally it adds the converted row
  into an ArrayList, which is returned in the Searcher class. I chose to return an ArrayList
  because when it comes to searching by a specific column, once I figured out what index the
  header is, I can easily search through that index at each row.
##### Searcher:  
Searcher takes in a Parser through the constructor, meaning that the user can
  define the type of Object the Searcher will search through (i.e., defining the T). It also has
  a boolean parameter that indicates whether there is a header or not. This class has an overall
  search method that based on whether there is a header or not, will call on either
  searchWantedCol or searchAllCols. The methods do exactly as their name suggests, searching by
  a specific column or searching all columns, as well as printing error messages if the data is
  malformed (has more values than the first row) or is missing values (has less values than the
  first row). Additionally, it will print if the value did not exist in the data. This class also
  takes care of cases with names as headers and integers as headers.

##### Utility: 
I then have a Utility class which combines both Searcher and Parser to create a level of
abstraction and be more friendly on the user's end.
The Utility has two constructors, so whether the user specifies a header to
  search through or not, it will call on the respective constructor. It also has a utility
  method that creates the instances of Searcher, Parser, a FileReader, and the CreatorFromRow
  Object, in this case an ArrayListCreator. I chose to use a FileReader as it was easy to
  directly pass in the file based on the user's input to the REPL; however, this works with
  a StringReader as well it just may not be as efficient. This class also checks if the file
  is within the correct directory in order to defend against access to other files/directories.

### DeveloperObjectClasses
I then have my classes that represent different Objects that the rows can be converted into. They
  all implement the CreatorFromRow interface in order to be properly parsed. Also they define the
  interface's create method so when the parser calls create(), the rows are converted as defined by
  the developer.
##### ArrayListCreator: 
This class converts each row into an ArrayList of Strings, so overall, the
  CSV would be an ArrayList<ArrayList<String>>. It defines the create method from the interface
  and converts the passed in row to an ArrayList.

##### Person and PersonCreator:
  Person is a simple class that represents a person Object. It has
  three fields to do so (name, age, job). All it does is define these fields for each new
  Person object created. PersonCreator implements the interface and specifies Person for the
  type. It then defines the create method to convert the passed in row into a Person object.

##### CreatorFromRow: 
This is an interface that represents an overall "type" for classes that
        implement it for the purpose of being parsed. It has one method declared called create,
        which the classes that implement the interface will define. This interface allows developers
        who want to use the parser to just provide their own Object they want to convert each row into
        and how they want to convert it. They then just need to implement the interface and the parser
        is able to work with whatever type the developer provides — strategy pattern!

### Misc. Classes
#### FactoryFailureException:
There is also the FactoryFailureException class, which is provided to catch errors while converting
the rows into Objects.

#### Main:
Main is the class begins the entire program by using a REPL to prompt the user for input.

# Errors/Bugs

### Regex Errors:
- #### Quotations 
    If a value in the CSV includes quotation marks around it ("Providence, RI"), the Regex will not 
filter out the quotation marks. I tested the value of something that had quotations in a CSV and 
asserted the equality of what the value was after it had been parsed. After being parsed, it still 
included quotes, which was expected.
- #### White Space
Some values especially values including a dollar sign has white space surrounding the beginning and 
end in the CSV. If the user did not include the white space, the searcher did not print out the row 
it was included in. To fix this, I trimmed the value as well as the input from the user in case the user
did decide to include the white space. Now the searcher will work for values including and not including
white space. 

# Tests
## Testing Suites:

### SearcherTest's tests:
I first have SearcherTest, which is the testing suite for Searcher. It uses PrintStreams to
obtain the messages from standard output and standard error when running the searcher on files.
I am able to compare this to the manual strings of what should be printing out and assert if
they are equal.
- simpleTestWithHeader() tests if the searcher works when the user specifies a header to
  search through. This ensures that the basic functionality of searching with a header is
  working
- simpleTestWithoutHeader() tests if the searcher works when the user does not specify a
header to search through. This ensures the basic functionality of searching without a header,
meaning it will search all the columns
- testWithIntegerHeader() tests when the user inputs an integer as the header to search through.
Specifically for this test, the headers are names and the user wants to search through the 5th
column (they would input 4). This ensures that cases where the headers don't match with
the user input it will still be able to search based on the integer value specified.
- testWithIntegerHeadNotIncluded() tests that the appropriate error message (Column specified
by integer is not included in data) will print if the user inputs a header's index that is not
included. For instance, if the user inputs 2 (column 3) but there are only 2 headers, it should
print the error message. This ensures that the searcher properly handles cases where the user
incorrectly inputs the header. Additionally, although the user did not input a valid column
the searcher will then search all columns in the file and print the row if the value was found.
- testWithValueInWrongCol() tests when the user wants to search through a specific column
but the value they are searching for is not in that column. In this case, an error message
will be printed telling the user that the value could not be found in that specific column.
This ensures that if the user specifies a column, the searcher will look through that
column only, and it is up to the user to provide the correct input.
- testWithExcessValuesAndMissingValues() tests a CSV file that has rows that don't match up
to the first column. This test ensures that if there are rows that are missing values or have
extra values, the searcher will properly inform the user of which row is malformed. 
- testInWrongDirectory() tests if a file is in the proper directory. This ensures that the
user is restricted to files included in the data directory. It will print out an error message
if the file does not exist in the correct directory.
- testWithWhiteSpace() tests that even though some values in the CSV have white space surrounding
it, the searcher will still be able to search for that value whether the user includes it or not.
This just ensures the searcher's functionality on a more technical complication.
- testUnfoundValueByComparingList() tests if the value is unfound by comparing the list returned
to an empty list. This just reensures the searcher's accuracy.
- testUnfoundValue() tests if the user is searching for a value that doesn't exist within
the data. This ensures that the searcher will properly handle and print out an error
message if the value is not found.
- testFileValidity() tests a file that does not exist. It will print an appropriate error 
message to the user. I decided to not System.exit() since the REPL will terminate after the
query already.

### ParserTest's tests:
  - testWithDifferentReader() tests the ability of Parser to parse with a different Reader
  Object. Within Utility, I create a FileReader to pass into Parser, so I decided to test
  a StringReader. I compared the two lists generated by calling parse with the StringReader
  and the FileReader. They should be equal since Parser can work with any Reader Object. This
  test ensures the ability of Parser to take in any Reader Object.
  - testWithPersonObject() tests the ability of Parser to parse an Object defined by a developer.
  As the developer, I created a Person Object and PersonCreator class to pass into Parser.
  This ensures that the Parser is able to be used by developers in any way they want. I check that
each value in the Person's respective field is equal to what it was before it was parsed by using
getter methods. 
  - testWithPersonObjectAndMalformedRows() tests parsing and converting a malformed CSV into a 
Person object. For the PersonCreator class, as the developer, I decided to throw a FactoryFailureException
if the size of the row does not match with the amount of fields. If it somehow does have the correct
amount of fields but the age field is empty, I catch the error of trying to convert an empty string to
an integer and place -1 in the field instead. Because the file I am testing on has malformed rows,
it will throw the exception, which is expected behavior.
  - testRegexQuotations() tests the error in the Regex by confirming that a value in a CSV that includes
quotations will still have quotations after being parsed.
# How to...
# Run and build program
Use the command mvn package to compile the program.
Use ./run to run the program.