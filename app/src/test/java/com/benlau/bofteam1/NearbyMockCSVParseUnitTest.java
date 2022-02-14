package com.benlau.bofteam1;

import com.benlau.bofteam1.db.Course;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.Assert.*;

public class NearbyMockCSVParseUnitTest {
    /**
     * Test for parsing mocked Student data given in a custom CSV format
     * Name,,,\n\nphotoURL,,,\n\nCourse1\n\nCourse2\n\n ...
     */
    @Test
    public void parseCustomCSVFormat() {
        String mockData = "Bill,,,\n" +
                "\n" +
                "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,\n" +
                "\n" +
                "2021,FA,CSE,210\n" +
                "\n" +
                "2022,WI,CSE,110\n" +
                "\n" +
                "2022,SP,CSE,110";
        ArrayList<String> list = new ArrayList<>(Arrays.asList(mockData.split(",,,")));
        mockData = this.joinString(list);
        list = new ArrayList<String>(Arrays.asList(mockData.split("\n\n")));
        assertEquals(list.get(0), "Bill");
        assertEquals(list.get(1), "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0");
        assertEquals(list.get(2), "2021,FA,CSE,210");
        assertEquals(list.get(3), "2022,WI,CSE,110");
        assertEquals(list.get(4), "2022,SP,CSE,110");
    }

    /**
     * Helper Method to convert ArrayList into custom String
     * @param s - Arraylist of Strings that will be converted into a string
     * @return string representing elements of ArrayList
     */
    public String joinString(ArrayList<String> s) {
        String string = "";
        for (int i = 0; i < s.size(); i++) {
            string = string + s.get(i);
        }
        return string;
    }

    /**
     * After splitting strings into ArrayList, mocking reading a Course string from the ArrayList
     * (aka from index 2 to ArrayList.size()-1) and parsing Course string into its fields
     * Year, Quarter, Course, CourseNumber
     */
    @Test
    public void parsingCourseDetailsFromString() {
        String course = "2021,FA,CSE,210";
        String[] stringArray;
        stringArray = course.split(",");

        assertEquals(stringArray[0], "2021");
        assertEquals(stringArray[1], "FA");
        assertEquals(stringArray[2], "CSE");
        assertEquals(stringArray[3], "210");
    }


}
