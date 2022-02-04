package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.benlau.bofteam1.db.DummyCourse;

public class UserClass extends AppCompatActivity {
    protected RecyclerView coursesRecyclerView;
    protected RecyclerView.LayoutManager coursesLayoutManager;
    protected CoursesViewAdapter coursesViewAdapter;

    protected ICourse[] data = {
            new DummyCourse("Fall", "2020",  "CSE",  "21"),
            new DummyCourse("Winter", "2020", "CSE", "30"),
            new DummyCourse("Spring", "2021",  "CSE",  "100")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_class);

        //TextView myCoursesView = findViewById(R.id.my_courses);
        //Intent intent = getIntent();

        //String[] courseNames = intent.getStringArrayExtra("my_courses");

        setTitle("Course History");
        //myCoursesView.setText(String.join("\n", courseNames));

        Spinner quarter = findViewById(R.id.quarter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"FA", "WI", "SP", "SS1", "SS2", "SSS"});
        quarter.setPrompt("QUARTER");
        quarter.setAdapter(adapter);

        coursesRecyclerView = findViewById(R.id.my_courses);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);

        coursesViewAdapter = new CoursesViewAdapter(data);
        coursesRecyclerView.setAdapter(coursesViewAdapter);


    }

    public void goBack(View view) {
        finish();
    }
}