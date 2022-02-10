package com.benlau.bofteam1.db;

public class DummyCourse{
    private final int id;
    private final String quarter;
    private final String year;
    private final String course;
    private final String number;

    public DummyCourse(int id, String quarter, String year, String course, String number){
        this.id = id;
        this.quarter = quarter;
        this.year = year;
        this.course = course;
        this.number = number;
    }

    public int getId(){
        return this.id;
    }

    public String getCourse(){
        String courseDisplay = (this.year + " " + this.quarter + " " + this.course + " " + this.number);
        return courseDisplay;
    }
}
