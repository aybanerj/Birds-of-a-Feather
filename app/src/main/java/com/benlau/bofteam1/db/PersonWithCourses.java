

/*
Don't think I need this intermediate class
package com.benlau.bofteam1.db;

import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;

public class PersonWithCourses implements IPerson {
    @Embedded
    public Person person;

    @Relation(parentColumn = "id",
              entityColumn = "person_id",
              entity = Course.class,
            projection = {"fullCourseName"})
    public List<String> courses;

    @Override
    public String getName() {
        return this.person.personName;
    }

    @Override
    public List<String> getCourses() {
        return this.courses;
    }
}


*/