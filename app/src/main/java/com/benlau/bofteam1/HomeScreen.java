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
import com.benlau.bofteam1.db.Person;

import java.util.List;

public class HomeScreen extends AppCompatActivity {
    protected RecyclerView studentsRecyclerView;
    protected RecyclerView.LayoutManager studentsLayoutManager;
    protected StudentsViewAdapter studentsViewAdapter;
    private AppDatabase db;

    protected DummyStudent[] data = {
            new DummyStudent("Ayushi", "https://cdn.sanity.io/images/2r0kdewr/production/82f8a09e19a456b94077b31d23861a5b03905307-1000x667.jpg", "2"),
            new DummyStudent("Victoria", "https://sandiegoburgercompany.com/wp-content/uploads/2020/07/mobile-header-bg.jpg", "3"),
            new DummyStudent("Mark", "https://betsylife.com/wp-content/uploads/2019/11/pho.jpg", "4")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("Birds of a Feather");

        db = AppDatabase.singleton(getApplicationContext());
        List<Person> persons = db.personsDao().getAllPeople();

        studentsRecyclerView = findViewById(R.id.student_view);
        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);

        studentsViewAdapter = new StudentsViewAdapter(persons);
        studentsRecyclerView.setAdapter(studentsViewAdapter);


    }
}