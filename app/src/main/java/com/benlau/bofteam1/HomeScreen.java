package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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


    /**
     * Method that creates the Home Screen activity, serving as a mainstay layout to display
     * a list of classmates that have had the same courses as the User
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("Birds of a Feather");

        db = AppDatabase.getDatabase(getApplicationContext());
        List<Person> persons = db.personsDao().getAllPeople();

        studentsRecyclerView = findViewById(R.id.student_view);
        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);

        studentsViewAdapter = new StudentsViewAdapter(persons);
        studentsRecyclerView.setAdapter(studentsViewAdapter);
    }

    /**
     * Method that launches the FakedMessageListener (Mocked Nearby) activity which
     * for now, allows external input of a specific, custom CSV style data which serves as
     * a mock for receving this data via Bluetooth
     *
     * @param view
     */
    public void onStartClicked(View view) {
        Intent intent = new Intent(this, FakedMessageListenerActivity.class);
        startActivity(intent);
    }


}