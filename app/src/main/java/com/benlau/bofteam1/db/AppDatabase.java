package com.benlau.bofteam1.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Person.class, Course.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // try to work out how not to use singletons if possible so we can test our own databases
    private static AppDatabase singletonInstance;

    public static AppDatabase singleton(Context context) {
            singletonInstance = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                    //need to not use allowMainThreatQueries as this can block main thread
                    .allowMainThreadQueries()
                    .build();

        return singletonInstance;
    }

    public static AppDatabase getDatabase(Context context){
        return singletonInstance;
    }
    public abstract CourseDAO coursesDao();
    public abstract PersonDao personsDao();
}