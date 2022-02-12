package com.benlau.bofteam1.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.benlau.bofteam1.ICourse;
//is it a table of people, or table of courses
@Entity(tableName = "course_history")
public class Course implements ICourse {
    public int id; //course id is not very useful
    public String year;
    public String quarter;
    public String courseName;
    public String courseNumber;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "fullCourse")
    public String fullCourse;
    //foreign key - references personId field in Person
    //use it to retrieve all Courses for a given Person
    @ColumnInfo(name = "person_id")
    public int personId;


    // add another column for id?
    // not adding rn because same course names should not be distinguishable

    // Constructor to add new courses
    public Course(){}
    public Course(int id, int personId, String year, String quarter, String courseName, String courseNumber){
        this.id = id;
        this.personId = personId; //is this different from id?
        this.year = year;
        this.quarter = quarter;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        String fullCourse = year + " " + quarter + " " + courseName + " " + courseNumber + " ID: " + id;
        this.fullCourse = fullCourse;
    }

    @Override
    public String getFullCourse(){
        return this.fullCourse;
    }


    @Override
    public int getId(){
        return this.id;
    }

    @Override
    public String getQuarter() {return this.quarter;}

    @Override
    public String getCourseName(){return this.courseName;}

    @Override
    public String getCourseNumber(){return this.courseNumber;}
}
