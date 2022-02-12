package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;

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

public class CourseHistoryActivity extends AppCompatActivity {
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

        //db = AppDatabase.singleton(getApplicationContext());
        db = AppDatabase.singleton(this);
        List<Course> courses = db.coursesDao().myCourses();

        Spinner quarter = findViewById(R.id.quarter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"FA", "WI", "SP", "SS1", "SS2", "SSS"});
        quarter.setPrompt("QUARTER");
        quarter.setAdapter(adapter);

        coursesRecyclerView = findViewById(R.id.my_courses);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);

        coursesViewAdapter = new CoursesViewAdapter(courses, (course) -> { db.coursesDao().delete(course);});
        coursesRecyclerView.setAdapter(coursesViewAdapter);


    }

    public void onAddCourseClicked(View view){
        int newCourseId = db.coursesDao().maxId() + 1;
        Spinner quarterSchool = (Spinner) findViewById(R.id.quarter);
        TextView numberTV = findViewById(R.id.courseID);
        TextView yearTV = findViewById(R.id.year);
        TextView courseTV = findViewById(R.id.course);
        String number = numberTV.getText().toString();
        String quarter = quarterSchool.getSelectedItem().toString();
        String year = yearTV.getText().toString();
        String course = courseTV.getText().toString();

        Course newCourse = new Course(newCourseId, year, quarter, course, number);
        if(year.equals("") || course.equals("") || number.equals(""))
        {
            Utilities.showAlert(this, "Missing Value");
        }
        else
        {
            /*
             if List<Courses> contains the newCourse

             */
            db.coursesDao().insert(newCourse);
            coursesViewAdapter.addCourse(newCourse);
            //TextView Output to Test Course Addition
            //TextView addedCourse = findViewById(R.id.testingList);
            //addedCourse.setText(newCourse.getCourse());
            //tests whether the newly added course is INDEED in the List of Courses List<Courses> courses
            //addedCourse.setText(String.valueOf(db.coursesDao().myCourses().contains(newCourse)));
            //String test = "";
            //for (int i = 0; i < db.coursesDao().myCourses().size(); i++) {
            //    test = test + " " + db.coursesDao().myCourses().get(i).getCourse();
            //}
            //addedCourse.setText(test);

        }
    }

    //todo :)
    //UNCOMMENT WHEN READY TO INTEGRATE HOMESCREEN!!
    /*
    public void onDoneClicked(View v) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }*/

    public void goBack(View view) {
        finish();
    }
}