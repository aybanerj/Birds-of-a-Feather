package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.benlau.bofteam1.db.AppDatabase;
import com.benlau.bofteam1.db.Course;
import com.benlau.bofteam1.db.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonFileDetailActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView URL_pic;
    private String url;
    private ListView l;
    private String name;
    //private int person_id;
    private String userUUID;
    private String newStudentUUID;
    private ArrayList<String> course = new ArrayList<>();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_file_detail);
        db = AppDatabase.getDatabase(getApplicationContext());


        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        name = intent.getStringExtra("person_name");
        //person_id = intent.getIntExtra("person_id",0);
        userUUID = intent.getStringExtra("userUUID");
        newStudentUUID = intent.getStringExtra("newStudentUUID");

        //course = (ArrayList<String>) getIntent().getSerializableExtra("common_courses");
        //Student person = db.studentsDao().getByUUID(userUUID);
        course = calculateCommonCourses(userUUID, newStudentUUID);

        //course.add(person.getCommonCourses());


        URL_pic = findViewById(R.id.image_url);
        LoadImage loadimage = new LoadImage(URL_pic);
        loadimage.execute(url);

        textView = findViewById(R.id.textView);
        textView.setText(name);

        l = findViewById(R.id.list);
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(this, R.layout.student_detail_row, course);
        l.setAdapter(arr);
    }

    public ArrayList<String> calculateCommonCourses(String userUUID, String newStudentUUID) {
        //List<Student> studentList = db.studentsDao().getAllStudents();
        //Student temp = studentList.get(studentList.size()-1);
        ArrayList<String> commonCourseNames = new ArrayList<String>();
//        String firstStudentUUID = studentList.get(0).getUUID();
//        String secondStudentUUID = studentList.get(1).getUUID();
//        String usersUUID = this.UUID;
//        String newStudentUUID = newStudent.getUUID();
        List<Course> coursesForNewPerson = db.coursesDao().getCoursesForStudent(userUUID);
        List<Course> coursesForAppUser = db.coursesDao().getCoursesForStudent(newStudentUUID);


        for (int i=0; i < coursesForNewPerson.size(); i++){
            for(int j=0; j < coursesForAppUser.size(); j++){
                if (coursesForNewPerson.get(i).getFullCourse().equalsIgnoreCase(coursesForAppUser.get(j).getFullCourse())){
                    commonCourseNames.add(coursesForAppUser.get(j).getFullCourse());
                }
            }
        }
        return commonCourseNames;
    }
}