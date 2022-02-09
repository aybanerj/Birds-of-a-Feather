package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.benlau.bofteam1.db.AppDatabase;
import com.benlau.bofteam1.db.Course;

import java.util.List;

public class HomeScreen extends AppCompatActivity {
    protected RecyclerView studentsRecyclerView;
    protected RecyclerView.LayoutManager studentsLayoutManager;
    protected StudentsViewAdapter studentsViewAdapter;
    private AppDatabase db;

    protected DummyStudent[] data = {
            new DummyStudent("Ayushi", "url", "3"),
            new DummyStudent("Victoria", "url", "2"),
            new DummyStudent("Mark", "url", "4")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("Birds of a Feather");

        studentsRecyclerView = findViewById(R.id.student_view);
        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);

        studentsViewAdapter = new StudentsViewAdapter(data);
        studentsRecyclerView.setAdapter(studentsViewAdapter);


    }
}