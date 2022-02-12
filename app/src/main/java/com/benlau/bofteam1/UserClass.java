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

import java.util.List;

public class UserClass extends AppCompatActivity {
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
        setContentView(R.layout.activity_user_class);
        setTitle("Course History");

        db = AppDatabase.singleton(getApplicationContext());
        List<Course> courses = db.coursesDao().getCoursesForPerson(0);//the first person

        Spinner quarter = findViewById(R.id.quarter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"FA", "WI", "SP", "SS1", "SS2", "SSS"});
        quarter.setPrompt("QUARTER");
        quarter.setAdapter(adapter);

        coursesRecyclerView = findViewById(R.id.my_courses);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);

        coursesViewAdapter = new CoursesViewAdapter(courses, (course) ->{ db.coursesDao().delete(course);});
        coursesRecyclerView.setAdapter(coursesViewAdapter);


        //Just going to insert some mocked values for now into databse to simulate classmates' courses
        //can predefine a .db file to preassign to database
        //Course classmate1 = new Course(newCourseId, year, quarter, course, number);


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

        Course newCourse = new Course(newCourseId, 0, year, quarter, course, number);
        if (year.equals("") || course.equals("") || number.equals("")) {
            Utilities.showAlert(this, "Missing Value");
        } else {
            db.coursesDao().getCoursesForPerson(0).add(newCourse);
            coursesViewAdapter.addCourse(newCourse);
        }
    }
    //public void  onEnterClicked(View view) {// adding  a course to a person courses list, gettin the personid
    /*
    Context context = view.getContext();
    Intent intent = new Intent (context, HomeScreen.class);
    context.startActivity(intent);
    */

    // getList using personID

   // db.personDa.insert(person)
    //then int newPersonId = db.personDao().maxId() + 1;

    // }


    public void onEnterClicked(View view){
        Context context = view.getContext();
        Intent intent = new Intent(context, HomeScreen.class);
        context.startActivity(intent);
    }

    public void goBack(View view) {
        finish();
    }
}