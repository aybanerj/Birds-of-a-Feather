package com.benlau.bofteam1.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.benlau.bofteam1.ICourse;

//is it a table of people, or table of courses

@Entity(tableName = "course_history")
public class Course implements ICourse {
    @PrimaryKey(autoGenerate = true)//what exactly is the id of a person set to
    public int id;

    public String year;
    public String quarter;
    public String courseName;
    public String courseNumber;
    public String classSize;

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
    public Course(int id, int personId, String year, String quarter, String courseName, String courseNumber, String classSize){
        this.id = id;
        this.personId = personId; //is this different from id?
        this.year = year;
        this.quarter = quarter;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.classSize = classSize;
        this.fullCourse = year + " " + quarter + " " + courseName + " " + courseNumber;
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
    public int getPersonId() {return this.personId;}

    @Override
    public String getQuarter() {return this.quarter;}

    @Override
    public String getCourseName(){return this.courseName;}

    @Override
    public String getCourseYear(){return this.year;}

    @Override
    public String getCourseNumber(){return this.courseNumber;}

    @Override
    public String getClassSize(){return this.classSize;}
}
