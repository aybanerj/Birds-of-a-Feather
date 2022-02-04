package com.benlau.bofteam1.db;


import com.benlau.bofteam1.ICourse;

public class DummyCourse implements ICourse {
    private final String quarter;
    private final String year;
    private final String course;
    private final String number;

    public DummyCourse(String quarter, String year, String course, String number){
        this.quarter = quarter;
        this.year = year;
        this.course = course;
        this.number = number;
    }

    @Override
    public String getCourse(){
        String courseDisplay = (this.year + " " + this.quarter + " " + this.course + " " + this.number);
        return courseDisplay;
    }
}
