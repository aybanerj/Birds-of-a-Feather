package com.benlau.bofteam1.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.benlau.bofteam1.ICourse;

//is it a table of people, or table of courses

@Entity(tableName = "course_history")
public class Course {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "courseId")
    private int courseId; //course id is not very useful

    private String year;
    private String quarter;
    private String courseName;
    private String courseNumber;
    private String courseSize;

    @NonNull
    @ColumnInfo(name = "fullCourse")
    private String fullCourse;
    //foreign key - references studentUUID field in Student
    //use it to retrieve all Courses for a given Student
    @ColumnInfo(name = "studentUUID")
    private String studentUUID;


    // add another column for id?
    // not adding rn because same course names should not be distinguishable

    // Constructor to add new courses
    public Course(String studentUUID, String year, String quarter, String courseName, String courseNumber, String courseSize){
        this.studentUUID = studentUUID;
        this.year = year;
        this.quarter = quarter;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.courseSize = courseSize;
        this.fullCourse = year + " " + quarter + " " + courseName + " " + courseNumber;
    }

    public String getFullCourse(){
        return this.fullCourse;
    }
    public void setFullCourse(String fullCourse) { this.fullCourse = fullCourse; }

    public String getStudentUUID()  {return this.studentUUID;}

    public int getCourseId(){
        return this.courseId;
    }
    public void setCourseId(int courseId) { this.courseId = courseId; }


    public String getYear() {return this.year;}
    public String getQuarter() {return this.quarter;}
    public String getCourseName(){return this.courseName;}
    public String getCourseNumber(){return this.courseNumber;}
    public String getCourseSize(){return this.courseSize;}
    public void setCourseSize(String courseSize) {this.courseSize = courseSize;}
}
