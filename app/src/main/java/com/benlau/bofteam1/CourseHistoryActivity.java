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

    /**
     * Method that launches the Course History Activity which allows the User to input his/her
     * class history.
     *
     * @param savedInstanceState
     */
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

        Spinner classSizes = findViewById(R.id.classSize_spinner);
        ArrayAdapter<String> classSizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Tiny(<40)", "Small(40-75)", "Medium(75-150)", "Large(150-250)", "Huge(250-400)", "Gigantic(400+)"});

        classSizes.setAdapter(classSizeAdapter);


        coursesRecyclerView = findViewById(R.id.my_courses);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);
        coursesViewAdapter = new CoursesViewAdapter(courses, (course) -> db.coursesDao().delete(course), this);
        coursesRecyclerView.setAdapter(coursesViewAdapter);


    }

    /**
     * Method that adds a user-inputted course when pressing an "Enter" button.
     * Manages the values that are sent to the corresponding layout XML file to populate UI
     *
     * @param view
     */
    public void onAddCourseClicked(View view) {
        int newCourseId = db.coursesDao().maxId() + 1;
        Spinner quarterSchool = (Spinner) findViewById(R.id.quarter);
        Spinner classSizeSpinner = (Spinner) findViewById(R.id.classSize_spinner);
        TextView numberTV = findViewById(R.id.courseID);
        TextView yearTV = findViewById(R.id.year);
        TextView courseTV = findViewById(R.id.course);
        String number = numberTV.getText().toString().toUpperCase() ;
        String quarter = quarterSchool.getSelectedItem().toString();
        String year = yearTV.getText().toString();
        String course = courseTV.getText().toString().toUpperCase();
        String classSize = classSizeSpinner.getSelectedItem().toString();

        Course newCourse = new Course(newCourseId, 1, year, quarter, course, number, classSize);

        //checks to ensure that fields are not left empty before moving on
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

    /**
     * Method that controls functionality of the Edit Button, which removes a course and  replaces
     * the textfields with the course details, enabling the User to quickly edit and re-add the course
     *
     * @param dataPassed
     */
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
            Spinner classSizes = findViewById(R.id.classSize_spinner);
            switch (data[4]) {
                case "Tiny(<40)":
                    classSizes.setSelection(0);
                    break;
                case "Small(40-75)":
                    classSizes.setSelection(1);
                    break;
                case "Medium(75-250)":
                    classSizes.setSelection(2);
                    break;
                case "Large(150-250)":
                    classSizes.setSelection(3);
                    break;
                case "Huge(250-400)":
                    classSizes.setSelection(4);
                    break;
                case "Gigantic(400+)":
                    classSizes.setSelection(5);
                    break;
                default:
                    break;
            }
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

    /**
     * Method that launches the Home Screen activity via pressing a Done button, signaling that
     * the User is done entering his/her course history.
     *
     * @param view
     */
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
