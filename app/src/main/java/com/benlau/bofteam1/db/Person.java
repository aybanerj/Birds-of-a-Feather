package com.benlau.bofteam1.db;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "persons")
public class Person {
    @PrimaryKey(autoGenerate = true)//want to keep updating person Id
    //id to reference a person by
    @ColumnInfo(name = "person_id")
    public int personId = 0; //start with 0, making static is bad practice
    @ColumnInfo(name = "firstName")
    private String personName;

    @ColumnInfo(name = "url")
    private String photoUrl;

    @ColumnInfo(name = "numCommon")
    private String commonCourses;
    /*
    @ColumnInfo(name = "commonCoursesWithAppUser")
    private List<String> commonCoursesWithAppUser;*/


    public Person(String personName, String photoUrl, String commonCourses){

        this.personName = personName;
        this.photoUrl = photoUrl;
        this.commonCourses = commonCourses;
        //this.commonCoursesWithAppUser = commonCoursesWithAppUser;
    }




    public int getPersonId() {
        return personId;
    }
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }
    public void setName(String name) {
        this.personName = name;
    }
    /*
    public void setCommonCoursesWithAppUser(List<String> courses) {
        this.commonCoursesWithAppUser = courses;
    }*/

    public String getPhotoUrl(){return photoUrl;}
    public void setPhotoUrl(String url){this.photoUrl = url;}

    public String getCommonCourses(){return commonCourses;}
    //public List<String> getCommonCoursesWithAppUser() {return commonCoursesWithAppUser;}

    public void setCommonCourses(String numCommon){this.commonCourses = numCommon;}



}
