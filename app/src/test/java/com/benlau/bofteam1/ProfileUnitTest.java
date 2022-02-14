package com.benlau.bofteam1;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.*;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;

public class ProfileUnitTest {
    /**
     * Test that tests 6 different names and verifies all states of logic that check for all
     * invalid name types
     */
    @Test
    public void validName() {
        String name1 = "Mark";
        String name2 = "MarkTheClasher123";
        String name3 = "#$&%^#*$*";
        String name4 = "Mark Anthony";
        String name5 = "qazwsxedcrfvtgbyhnujmikolpqazwssxedcrfvtgbyhnujmikolpqazwsxedcrfvtgbyhnujmikolpqazwsxedcrfvtgbyhnujmikopqazwsxcqazwsxedcrfvtgbyh";
        String name6 = "";
        int state = 0;
        String regex = "^[a-zA-Z- ]*$";

        //TESTING NAME1
        if (name1.length() > 100) {
            state = 1;
        }
        else if (name1.length() <= 0) {
            state = 2;
        }
        else if (!name1.matches(regex)) {
            state = 3;
        }
        assertEquals(state, 0);

        //TESTING NAME 2
        if (name2.length() > 100) {
            state = 1;
        }
        else if (name2.length() <= 0) {
            state = 2;
        }
        else if (!name2.matches(regex)) {
            state = 3;
        }
        assertEquals(state, 3);


        //TESTING NAME3
        if (name3.length() > 100) {
            state = 1;
        }
        else if (name3.length() <= 0) {
            state = 2;
        }
        else if (!name3.matches(regex)) {
            state = 3;
        }
        assertEquals(state, 3);


        //TESTING NAME4
        if (name4.length() > 100) {
            state = 1;
        }
        else if (name4.length() <= 0) {
            state = 2;
        }
        else if (!name4.matches(regex)) {
            state = 3;
        }
        assertEquals(state, 3);

        //TESTING NAME5
        if (name5.length() > 100) {
            state = 1;
        }
        else if (name5.length() <= 0) {
            state = 2;
        }
        else if (!name5.matches(regex)) {
            state = 3;
        }
        assertEquals(state, 1);


        //TESTING NAME6
        if (name6.length() > 100) {
            state = 1;
        }
        else if (name6.length() <= 0) {
            state = 2;
        }
        else if (!name6.matches(regex)) {
            state = 3;
        }
        assertEquals(state, 2);
    }




}
