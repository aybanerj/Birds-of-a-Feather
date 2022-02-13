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
import com.benlau.bofteam1.db.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonFileDetailActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView URL_pic;
    private String url;
    private ListView l;
    private String name;
    private int person_id;
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
        person_id = intent.getIntExtra("person_id",0);

        //course = (ArrayList<String>) getIntent().getSerializableExtra("common_courses");
        Person person = db.personsDao().getPersonByname(name);
        course = calculateCommonCourses(person);

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

    public ArrayList<String> calculateCommonCourses(Person newPerson) {
        List<Person> personList = db.personsDao().getAllPeople();
        Person temp = personList.get(personList.size()-1);
        ArrayList<String> commonCourseNames = new ArrayList<String>();
        List<Course> coursesForNewPerson = db.coursesDao().getCoursesForPerson(temp.getPersonId());
        List<Course> coursesForAppUser = db.coursesDao().getCoursesForPerson(1);


        for (int i=0; i < coursesForNewPerson.size(); i++){
            for(int j=0; j < coursesForAppUser.size(); j++){
                if (coursesForNewPerson.get(i).getFullCourse().toUpperCase().equals(coursesForAppUser.get(j).getFullCourse().toUpperCase())){
                    commonCourseNames.add(coursesForAppUser.get(j).getFullCourse());
                }
            }
        }
        return commonCourseNames;
    }
}