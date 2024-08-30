package edu.brown.cs.student.main.DeveloperObjectClasses;

/** A class that represents a Person */
public class Person {
  private String name;
  private int age;
  private String job;

  /**
   * Person's constructor, which sets the fields of a Person object
   *
   * @param name String that represents a person's name
   * @param age int that represents a person's age
   * @param job String that represents a person's job
   */
  public Person(String name, int age, String job) {
    this.name = name;
    this.age = age;
    this.job = job;
  }

  /**
   * Getter that returns the Person object's name
   *
   * @return
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter that returns the Person object's age
   *
   * @return
   */
  public int getAge() {
    return this.age;
  }

  /**
   * Getter that returns the Person object's job
   *
   * @return
   */
  public String getJob() {
    return this.job;
  }
}
