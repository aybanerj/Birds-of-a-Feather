package com.benlau.bofteam1.db;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "students")
public class Student {
    //id to reference a person by
    //@PrimaryKey(autoGenerate = true)//want to keep updating person Id
    //@ColumnInfo(name = "studentId")
    //public int studentId = 0; //start with 0, making static is bad practice

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "UUID")
    private String UUID;

    @ColumnInfo(name = "firstName")
    private String studentName;

    @ColumnInfo(name = "url")
    private String photoUrl;

    @ColumnInfo(name = "numCommon")
    private String numCommonCourses;

    /*
    @ColumnInfo(name = "commonCoursesWithAppUser")
    private List<String> commonCoursesWithAppUser;*/


    public Student(String studentName, String photoUrl, String numCommonCourses, String UUID){
        this.studentName = studentName;
        this.photoUrl = photoUrl;
        this.numCommonCourses = numCommonCourses;
        this.UUID = UUID;
        //this.commonCoursesWithAppUser = commonCoursesWithAppUser;
    }



    /*
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }*/

    public String getUUID() { return this.UUID; }
    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getStudentName() {
        return this.studentName;
    }
    public void setName(String studentName) {
        this.studentName = studentName;
    }
    /*
    public void setCommonCoursesWithAppUser(List<String> courses) {
        this.commonCoursesWithAppUser = courses;
    }*/

    public String getPhotoUrl(){return this.photoUrl;}
    public void setPhotoUrl(String url){this.photoUrl = url;}

    public String getNumCommonCourses(){return this.numCommonCourses;}
    public void setNumCommonCourses(String numCommon){this.numCommonCourses = numCommon;}

    //public List<String> getCommonCoursesWithAppUser() {return commonCoursesWithAppUser;}

}
