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

        if (receivedProfile.getText().toString().equals("")) {
            Utilities.showAlert(this,"Please Input Mocked Information");
        }
        else {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(receivedString.split(",,,")));
            receivedString = this.joinString(list);
            list = new ArrayList<String>(Arrays.asList(receivedString.split("\n\n")));
            receivedProfile.setText("");

            int newCourseId = db.coursesDao().maxId() + 1;
            Course newCourse;
            Person newPerson = new Person(list.get(0), list.get(1), "0");
            db.personsDao().insert(newPerson);

            List<Person> personList = db.personsDao().getAllPeople();
            Person temp = personList.get(personList.size()-1);
            String[] stringArray;
            //assuming list size at least 2
            for (int i = 2; i < list.size(); i++) {
                stringArray = list.get(i).split(",");
                newCourse = new Course(0, temp.getPersonId(), stringArray[0], stringArray[1], stringArray[2], stringArray[3]);
                db.coursesDao().insert(newCourse);
            }





        /*
        ArrayList<String> list = new ArrayList<>(Arrays.asList(receivedString.split(",,,")));
        receivedString = this.joinString(list);
        list = new ArrayList<String>(Arrays.asList(receivedString.split("\n\n")));
        receivedProfile.setText("");

        int newCourseId = db.coursesDao().maxId() + 1;
        Course newCourse;
        Person newPerson = new Person(list.get(0), list.get(1), "0");
        db.personsDao().insert(newPerson);

        List<Person> personList = db.personsDao().getAllPeople();
        Person temp = personList.get(1);
        //assuming list size at least 2
        String[] stringArray;
        for (int i = 2; i < list.size(); i++) {
            stringArray = list.get(i).split(",");
            newCourse = new Course(newCourseId, temp.getPersonId(), stringArray[0], stringArray[1], stringArray[2], stringArray[3]);
            //db.coursesDao().getCoursesForPerson(newPerson.getPersonId()).add(newCourse);
            db.coursesDao().insert(newCourse);
            //newCourseId++;
        }*/
            //studentsViewAdapter.addStudent(newPerson);
            calculateCommonCourses(newPerson);

            personList = db.personsDao().getAllPeople();

            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
        }


    }
    public void calculateCommonCourses(Person newPerson) {
        List<Person> personList = db.personsDao().getAllPeople();
        Person temp = personList.get(personList.size()-1);
        List<String> commonCourseNames = new ArrayList<String>();
        int commonCounter = 0;
        List<Course> coursesForNewPerson = db.coursesDao().getCoursesForPerson(temp.getPersonId());
        List<Course> coursesForAppUser = db.coursesDao().getCoursesForPerson(1);

        for (int i=0; i < coursesForNewPerson.size(); i++){
            for(int j=0; j < coursesForAppUser.size(); j++){
                if (coursesForNewPerson.get(i).getFullCourse().toUpperCase().equals(coursesForAppUser.get(j).getFullCourse().toUpperCase())){
                    commonCounter++;
                    commonCourseNames.add(coursesForAppUser.get(j).getFullCourse());
                }
            }

        temp.setCommonCourses(String.valueOf(commonCounter));
        db.personsDao().UpdatePerson(temp);
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