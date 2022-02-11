package com.benlau.bofteam1.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

    @Dao
    public interface PersonDao
    {
        //findin a way to get the personId and persons
        @Transaction
        @Query("SELECT * FROM persons")
        //List<Course> getCoursesForPerson(int personId);
        List<Person> getAllPeople();
        //need getAll to retrieve all courses for a givenPerson
        @Query("SELECT * FROM persons WHERE person_id =:personId")
        Person get(int personId);


        @Insert
        void insert(Person person);





    }


