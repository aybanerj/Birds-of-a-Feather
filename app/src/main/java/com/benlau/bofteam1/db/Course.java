package com.benlau.bofteam1.db;

public class Course {
    public String year;
    public String quarter;
    public String courseName;
    public String courseNumber;

    Course(String year, String quarter, String courseName, String courseNumber){
        this.year = year;
        this.quarter = quarter;
        this.courseName = quarter;
        this.courseNumber = courseNumber;
    }

    public String courseConcat(){
        String combined = this.year+this.quarter+this.courseName+this.courseNumber;
        return combined;
    }

}
