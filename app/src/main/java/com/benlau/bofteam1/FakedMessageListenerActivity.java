package com.benlau.bofteam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benlau.bofteam1.db.AppDatabase;
import com.benlau.bofteam1.db.Course;
import com.benlau.bofteam1.db.Student;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FakedMessageListenerActivity extends AppCompatActivity {
    private MessageListener messageListener;
    private MessageListener realListener;
    //private static final String TAG = "HERE I AM FOR VISIBILITY IN THE LOG";
    private String receivedString = "";
//    protected RecyclerView studentsRecyclerView;
//    protected RecyclerView.LayoutManager studentsLayoutManager;
//    protected StudentsViewAdapter studentsViewAdapter;
    private AppDatabase db;
    private String userUUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //from the start button on the home screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faked_message_listener);
        db = AppDatabase.getDatabase(getApplicationContext());
//        List<Person> persons = db.personsDao().getAllPeople();
//        studentsRecyclerView = findViewById(R.id.student_view);
//        studentsLayoutManager = new LinearLayoutManager(this);
//        studentsRecyclerView.setLayoutManager(studentsLayoutManager);
//
//        studentsViewAdapter = new StudentsViewAdapter(persons);
//        studentsRecyclerView.setAdapter(studentsViewAdapter);
/*
        this.realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                Log.d(TAG, "Received a Message" + new String(message.getContent()));
            }

            @Override
            public void onLost(@NonNull Message message) {
                Log.d(TAG, "No More Message" + new String(message.getContent()));
            }
        };
        messageListener = new FakedMessageListener(realListener, 3, this.receivedString);*/

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userUUID = extras.getString("UUID");
        }

    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        //Nearby.getMessagesClient(this).subscribe(messageListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Nearby.getMessagesClient(this).unsubscribe(messageListener);
    }*/

    public String joinString(ArrayList<String> s) {
        String string = "";
        for (int i = 0; i < s.size(); i++) {
            string = string + s.get(i);
        }
        return string;
    }

    /**
     * Method to head back to the HomeScreenActivity
     * @param view
     */
    public void onEnterClicked(View view) {
        EditText receivedProfile = findViewById(R.id.NearbyMessageMockedTextbox);
        this.receivedString = receivedProfile.getText().toString();
        //this.messageListener = new FakedMessageListener(this.realListener, 3, this.receivedString);
        String tempString = receivedProfile.getText().toString();
        boolean wave = false;
        if (receivedProfile.getText().toString().equals("")) {
            Utilities.showAlert(this,"Please Input Mocked Information");
        }
        else {
            if (this.receivedString.charAt(this.receivedString.length()-1) == ',') {
                wave = true;
            }
            receivedProfile.setText("");
            ArrayList<String> list = new ArrayList<>(Arrays.asList(this.receivedString.split(",,,,")));
            this.receivedString = this.joinString(list);
            list = new ArrayList<>(Arrays.asList(this.receivedString.split("\n")));
            Student newStudent = new Student(list.get(1), list.get(2), "0", list.get(0));
            db.studentsDao().insert(newStudent);
            Course newCourse;
            String[] courseFields;
            if (wave == true) {
                //take care of last row UUID and wave after
                for (int i = 3; i < list.size()-1;  i++) {
                    courseFields = list.get(i).split(",");
                    newCourse = new Course(list.get(0), courseFields[0], courseFields[1], courseFields[2], courseFields[3], courseFields[4]);
                    db.coursesDao().insert(newCourse);
                }
                //the User's UUID to confirm that the incoming UUID matches the User's to confirm Wave
                list = new ArrayList<>(Arrays.asList(list.get(list.size()-1).split(",")));
            }
            else {
                //normal parse
                for (int i = 3; i < list.size(); i++) {
                    courseFields = list.get(i).split(",");
                    newCourse = new Course(list.get(0), courseFields[0], courseFields[1], courseFields[2], courseFields[3], courseFields[4]);
                    db.coursesDao().insert(newCourse);
                }
            }
            calculateCommonCourses(newStudent);
            Intent intent = new Intent(this, HomeScreen.class);
            intent.putExtra("Information", tempString);
            intent.putExtra("Wave", wave);
            intent.putExtra("UUID", userUUID);
            startActivity(intent);
        }

    }

    /**
     * Method that calculates the number of common Courses between the User and a Classmate
     * and sets this number in the commonCourses field for the Classmate stored in the database
     *
     * @param newStudent
     */
    public void calculateCommonCourses(Student newStudent) {
        //List<Student> studentList = db.studentsDao().getAllStudents();
        //Student temp = studentList.get(studentList.size()-1);
        List<String> commonCourseNames = new ArrayList<String>();
        int commonCounter = 0;
        List<Course> coursesForNewPerson = db.coursesDao().getCoursesForStudent(newStudent.getUUID());
        String newStudentUUID = newStudent.getUUID();
        //needs to have carried the original user UUID all the way here
        List<Course> coursesForAppUser = db.coursesDao().getCoursesForStudent(this.userUUID);
        String b = "buffer";
        for (int i=0; i < coursesForNewPerson.size(); i++){
            for(int j=0; j < coursesForAppUser.size(); j++){
                //temporarily removing the uppercase
                if (coursesForNewPerson.get(i).getFullCourse().equals(coursesForAppUser.get(j).getFullCourse())){
                    commonCounter++;
                    commonCourseNames.add(coursesForAppUser.get(j).getFullCourse());
                }
            }
        newStudent.setNumCommonCourses(String.valueOf(commonCounter));
        db.studentsDao().UpdateStudent(newStudent);
        }
    }

}

/*
a4ca50b6-941b-11ec-b909-0242ac120002,,,,
Bill,,,,
https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,
2021,FA,CSE,210,Small
2022,WI,CSE,110,Large
4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,
 */

/*
a4ca50b6-941b-11ec-b909-0242ac1200041,,,,
Inigo,,,,
https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,
2021,FA,MUS,19,Large
2022,WI,CSE,110,Large
4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,
 */
