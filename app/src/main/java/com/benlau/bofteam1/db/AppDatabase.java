//package com.benlau.bofteam1.db;
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//@Database(entities = {Course.class}, version = 1)
//public abstract class AppDatabase extends RoomDatabase {
//
//    private static AppDatabase singletonInstance;
//
//    public static AppDatabase singleton(Context context) {
//        if (singletonInstance == null) {
//            singletonInstance = Room.databaseBuilder(context, AppDatabase.class, "course.db")
//                    .allowMainThreadQueries()
//                    .build();
//        }
//
//        return singletonInstance;
//    }
//
//    public abstract CourseDAO CourseDAO();
//}