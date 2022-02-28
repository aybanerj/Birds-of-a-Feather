package com.benlau.bofteam1.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

    @Dao
    public interface StudentDao
    {
        @Transaction
        @Query("SELECT * FROM students")
        List<Student> getAllStudents();
        /*
        @Query("SELECT * FROM students WHERE studentId =:studentID")
        Student getByID(int studentId);
        */
        /*
        @Query("SELECT * FROM students WHERE firstName =:name")
        Student getPersonByname(String name);
        */

        @Query("SELECT * FROM students WHERE UUID =:UUID")
        Student getByUUID(String UUID);

        @Update
        public void UpdateStudent(Student student);

        @Insert
        void insert(Student student);

        @Update
        void upsert(Student student);
    }


