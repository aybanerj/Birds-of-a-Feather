package com.benlau.bofteam1.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CourseDAO
{
    @Transaction
    @Query("SELECT * FROM course_history where person_id=:personId")
    List<Course> getCoursesForPerson(int personId);

    @Insert
    void insert(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT COUNT(*) from course_history")
    int count();

    @Query("SELECT MAX(id) from course_history")
    int maxId();

}
