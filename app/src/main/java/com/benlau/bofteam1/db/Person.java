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

    //not sure if this needs to be its own column
    @ColumnInfo(name = "favorites")
    private boolean isFavorite;

    @ColumnInfo(name = "classSizeWeight")
    private double classSizeWeight;

    @ColumnInfo(name = "recencyScore")
    private int recencyScore;

    /*
    @ColumnInfo(name = "commonCoursesWithAppUser")
    private List<String> commonCoursesWithAppUser;*/


    public Person(String personName, String photoUrl, String commonCourses){
        this.isFavorite = false; //does this need to be passed in, or is default always false, and is it if user favorites this person or
        //otherway around
        this.recencyScore = 0;
        this.classSizeWeight = 0;
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
    public int getRecencyScore(){return this.recencyScore;}

    public void setRecencyScore(int recencyScore) {
        this.recencyScore = recencyScore;
    }

    public double getClassSizeWeight(){return this.classSizeWeight;}

    public void setClassSizeWeight(double num) {this.classSizeWeight=num;}

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

    public void setIsFavorite(boolean value){this.isFavorite = value;}//can switch on and off
    public boolean getIsFavorite(){return isFavorite;}

}
