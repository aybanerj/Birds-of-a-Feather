package com.benlau.bofteam1.db;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "persons")
public class Person {
    @PrimaryKey(autoGenerate = true)//want to keep updating person Id
    //id to reference a person by
    @ColumnInfo(name = "person_id")
    private int personId = 0; //start with 0, making static is bad practice
    @ColumnInfo(name = "firstName")
    private String personName;

    @ColumnInfo(name = "url")
    private String photoUrl;



    public Person(String personName, String photoUrl){

        this.personName = personName;
        this.photoUrl = photoUrl;
    }




    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return personName;
    }

    public void setName(String name) {
        this.personName = name;
    }
}
