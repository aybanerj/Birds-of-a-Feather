package com.benlau.bofteam1.db;

import com.benlau.bofteam1.ICourse;

import java.util.Arrays;
import java.util.List;

public class dummyCourses implements ICourse
{
    private final int id;
    private final String name;
    private final String imageURL;
    private final String[] courses;

    public dummyCourses(int id, String name, String imageURL, String[] courses)
    {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.courses = courses;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String imageURL() {
        return imageURL;
    }

    @Override
    public List<String> getCourses() {
        return Arrays.asList(courses);
    }
}
