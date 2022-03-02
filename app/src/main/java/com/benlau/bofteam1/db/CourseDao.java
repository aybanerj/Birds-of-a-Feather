package com.benlau.bofteam1.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CourseDao
{
    @Transaction
    @Query("SELECT * FROM course_history where studentUUID=:UUID")
    List<Course> getCoursesForStudent(String UUID);

    /*
    @Query("SELECT * FROM course_history WHERE person_id=:personId")
    Course get(int personId);*/

    @Insert
    void insert(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT COUNT(*) FROM course_history")
    int count();

    @Query("SELECT MAX(courseId) FROM course_history")
    int maxId();

}
