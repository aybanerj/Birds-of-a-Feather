package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benlau.bofteam1.db.AppDatabase;
import com.benlau.bofteam1.db.Course;
import com.benlau.bofteam1.db.Person;
//import com.google.android.gms.nearby.Nearby;
//import com.google.android.gms.nearby.messages.MessageListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FakedMessageListenerActivity extends AppCompatActivity {
    //private MessageListener messageListener;
//    protected RecyclerView studentsRecyclerView;
//    protected RecyclerView.LayoutManager studentsLayoutManager;
//    protected StudentsViewAdapter studentsViewAdapter;
    private AppDatabase db;


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
    }

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
        String receivedString = receivedProfile.getText().toString();
        /*
        read in data entered into a textbox formatted in a csv file format via file I/O
        note the commas to determine name/URL/classes
        separate and place into string arrayList
        split the string here until every part is an element in an arrayList
        insert into database/add this person in the same way that you added the others
         */
        ArrayList<String> list = new ArrayList<>(Arrays.asList(receivedString.split("\n\n")));
        receivedString = this.joinString(list);
        list = new ArrayList<String>(Arrays.asList(receivedString.split(",")));
        list.removeAll(Collections.singleton(""));
        receivedProfile.setText("");

        //HMMM maybe gotta modify the database

        /*
        for the ArrayList
        1st elem 0: Name
        2nd elem 1: Photo URL
        3rd-6th elem 2-5: Year, Quarter, Course, Number
        7th-10th elem 6-9: Year, Quarter, Course, Number
        ...
        */
        int newCourseId = db.coursesDao().maxId() + 1;
        Course newCourse;
        Person newPerson = new Person(list.get(0), list.get(1), "0", new ArrayList<String>());
        db.personsDao().insert(newPerson);

        //assuming list size at least 2
        for (int i = 2; i < list.size()-4; i++) {
            newCourse = new Course(newCourseId, newPerson.getPersonId(), list.get(i), list.get(i+1), list.get(i+2), list.get(i+3));
            db.coursesDao().getCoursesForPerson(newPerson.getPersonId()).add(newCourse);
        }
        //studentsViewAdapter.addStudent(newPerson);
        calculateCommonCourses(newPerson);

        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
    public void calculateCommonCourses(Person newPerson) {
        List<String> commonCourseNames = new ArrayList<String>();
        int commonCounter = 0;
        List<Course> coursesForNewPerson = db.coursesDao().getCoursesForPerson(newPerson.getPersonId());
        List<Course> coursesForAppUser = db.coursesDao().getCoursesForPerson(0);

        for (int i=0; i < coursesForNewPerson.size(); i++){
            for(int j=0; j < coursesForAppUser.size(); j++){
                if (coursesForNewPerson.get(i).getFullCourse().toUpperCase().equals(coursesForAppUser.get(j).getFullCourse().toUpperCase())){
                    commonCounter++;
                    commonCourseNames.add(coursesForAppUser.get(j).getFullCourse());
                }
            }
        newPerson.setCommonCourses(String.valueOf(commonCounter));
        newPerson.setCommonCoursesWithAppUser(commonCourseNames);
        }


    }




//    @Override
//    protected void onStart() {
//        super.onStart();
//        Nearby.getMessagesClient(this).subscribe(messageListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Nearby.getMessagesClient(this).unsubscribe(messageListener);
//    }


}