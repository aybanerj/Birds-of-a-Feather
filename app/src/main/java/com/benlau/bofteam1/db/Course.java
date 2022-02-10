package com.benlau.bofteam1.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_history")
public class Course {
    public String year;
    public String quarter;
    public String courseName;
    public String courseNumber;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public int courseId;

    @NonNull
    @ColumnInfo(name = "fullCourse")
    public String fullCourse;

    // add another column for id?
    // not adding rn because same course names should not be distinguishable

    // Constructor to add new courses
    public Course(){}
    public Course(int id, String year, String quarter, String courseName, String courseNumber){
        this.courseId = id;
        this.year = year;
        this.quarter = quarter;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        String fullCourse = year + " " + quarter + " " + courseName + " " + courseNumber;
        this.fullCourse = fullCourse;
    }
    public String getCourse(){
        return this.fullCourse;
    }
    public int getId(){
        return this.courseId;
    }
}
