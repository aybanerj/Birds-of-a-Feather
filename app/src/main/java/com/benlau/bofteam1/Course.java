package com.benlau.bofteam1;

enum Quarter { WI, SP, S1, S2, SS, FA }

public class Course {
  private String year;
  private Quarter quarter;
  private String subject;
  private String number;

  public Course (String Y, Quarter Q, String S, String N) {
    year = Y;
    quarter = Q;
    subject = S;
    number = N;
  }


  public String getYear() {
    return year;
  }

  public String getQuarter() {
    return quarter.name();
  }

  public String getSubject() {
    return subject;
  }

  public String getNumber() {
    return number;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Course course = (Course) o;
    return year.equals(course.year) && quarter == course.quarter && subject.equals(course.subject) && number.equals(course.number);
  }
}
