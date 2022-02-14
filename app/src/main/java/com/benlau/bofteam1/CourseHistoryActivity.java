package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.benlau.bofteam1.db.AppDatabase;
import com.benlau.bofteam1.db.Course;

import com.benlau.bofteam1.db.CoursesViewAdapter;

import java.util.List;

public class CourseHistoryActivity extends AppCompatActivity implements OnEditClickListener {
    protected RecyclerView coursesRecyclerView;
    protected RecyclerView.LayoutManager coursesLayoutManager;
    protected CoursesViewAdapter coursesViewAdapter;
    private AppDatabase db;


//    protected ICourse[] data = {
//            new DummyCourse(0, "Fall", "2020",  "CSE",  "21"),
//            new DummyCourse(1, "Winter", "2020", "CSE", "30"),
//            new DummyCourse(2, "Spring", "2021",  "CSE",  "100")
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_history);
        setTitle("Course History");
        db = AppDatabase.getDatabase(getApplicationContext());
        List<Course> courses = db.coursesDao().getCoursesForPerson(0);//the first person

        //db = AppDatabase.singleton(getApplicationContext());
        //db = AppDatabase.singleton(this);
        //List<Course> courses = db.coursesDao().myCourses();

        Spinner quarter = findViewById(R.id.quarter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"FA", "WI", "SP", "SS1", "SS2", "SSS"});
        quarter.setPrompt("QUARTER");
        quarter.setAdapter(adapter);

        coursesRecyclerView = findViewById(R.id.my_courses);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);
        coursesViewAdapter = new CoursesViewAdapter(courses, (course) -> db.coursesDao().delete(course), this);
        coursesRecyclerView.setAdapter(coursesViewAdapter);


    }

    public void onAddCourseClicked(View view) {
        //need to also generate newPersonId that changes.
        // int newPersonId = db.coursesDao().maxId() +1; only put in the person in the db once all courses are added
        //when add course, put that course in the current person's list, keep updating person. Only change perosn id at the end when adding the person to db


        int newCourseId = db.coursesDao().maxId() + 1;
        Spinner quarterSchool = (Spinner) findViewById(R.id.quarter);
        TextView numberTV = findViewById(R.id.courseID);
        TextView yearTV = findViewById(R.id.year);
        TextView courseTV = findViewById(R.id.course);
        String number = numberTV.getText().toString();
        String quarter = quarterSchool.getSelectedItem().toString();
        String year = yearTV.getText().toString();
        String course = courseTV.getText().toString();

        Course newCourse = new Course(newCourseId, 1, year, quarter, course, number);
        if(year.equals(""))
        {
            Utilities.showAlert(this, "Missing Value: year\nex: 20XX");
        }
        else if(course.equals(""))
        {
            Utilities.showAlert(this, "Missing Value: cours\nex: CSE");
        }
        else if(number.equals(""))
        {
            Utilities.showAlert(this, "Missing Value: numbe\nex: 8A");
        }
        else
        {
            db.coursesDao().insert(newCourse);
            coursesViewAdapter.addCourse(newCourse);
        }
    }

    @Override
    public void onEditClick(String dataPassed) {
        if(dataPassed != null) {
            String[] data = dataPassed.split(" ");

            TextView numberTV = findViewById(R.id.courseID);
            TextView yearTV = findViewById(R.id.year);
            TextView courseTV = findViewById(R.id.course);
            yearTV.setText(data[0]);
            courseTV.setText(data[2]);
            numberTV.setText(data[3]);
            Spinner quarterSchool = findViewById(R.id.quarter);
            switch (data[1]) {
                case "FA":
                    quarterSchool.setSelection(0);
                    break;
                case "WI":
                    quarterSchool.setSelection(1);
                    break;
                case "SP":
                    quarterSchool.setSelection(2);
                    break;
                case "SS1":
                    quarterSchool.setSelection(3);
                    break;
                case "SS2":
                    quarterSchool.setSelection(4);
                    break;
                case "SSS":
                    quarterSchool.setSelection(5);
                    break;
                default:
                    break;
            }
        }
    }

    public void onDoneClicked(View view) {

        //Checks course history to ensure it is not empty
        if (db.coursesDao().count() <= 0) {
            Utilities.showAlert(this,"Please Input Courses");
        }
        else {
            Context context = view.getContext();
            Intent intent = new Intent(this, HomeScreen.class);
            context.startActivity(intent);
        }

    }

    public void goBack(View view) {
        finish();
    }
}
