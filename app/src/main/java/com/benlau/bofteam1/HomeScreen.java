package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.benlau.bofteam1.db.AppDatabase;
import com.benlau.bofteam1.db.Course;
import com.benlau.bofteam1.db.Person;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeScreen extends AppCompatActivity {
    protected RecyclerView studentsRecyclerView;
    protected RecyclerView.LayoutManager studentsLayoutManager;
    protected StudentsViewAdapter studentsViewAdapter;
    boolean onCreateCall = true;
    List<Person> persons;

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

        //populating filter dropdown menu
        Spinner sortingDropdown = findViewById(R.id.filter_dropdown);
        String[] sortingDropdownOptions = new String[]{"prioritize recent", "prioritize small classes", "this quarter only"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,sortingDropdownOptions);
        sortingDropdown.setAdapter(adapter);

        db = AppDatabase.getDatabase(getApplicationContext());
         persons = db.personsDao().getAllPeople();
        //can somehow update persons whenever doing sorting/filtering?

        studentsRecyclerView = findViewById(R.id.student_view);
        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);


        studentsViewAdapter = new StudentsViewAdapter(persons);
        studentsRecyclerView.setAdapter(studentsViewAdapter);

       //persons = sortRecyclerViewInGeneralByCommonCourses(persons);//initially sort the bof list by just common courses
        //studentsViewAdapter.notifyDataSetChanged();




        //since spinner click listener will be triggered when onCreate starts and when user selects it,
        //onCreate call should have false flag so default list shown
        sortingDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 //if flag is false, then check if prioritize recent, prioritize small classes
                 //or this quarter only
                if (onCreateCall) {
                    onCreateCall = false;
                }
                else {
                    String sortingOption = (String)adapterView.getItemAtPosition(i);//can you cast it to string?
                    switch(sortingOption){
                        case "prioritize recent":
                               sortByRecent(persons);
                               studentsViewAdapter.notifyDataSetChanged();
                                break;
                        case "prioritize small classes":
                               sortBySmallClasses(persons);
                               studentsViewAdapter.notifyDataSetChanged();
                                break;
                        case "this quarter only":
                                //for now dummy value for this quarter is 2022 WI
                                sortByThisQuarterOnly(persons);
                                studentsViewAdapter.notifyDataSetChanged();
                                break;
                        default:
                            break;

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //default so no sorting


                return;
            }
        });


    }
    public void sortByRecent(List<Person> persons){
        //set doesn't allow duplicates
        List<Course> allCourses = db.coursesDao().getAllCourses();
        Collections.sort(allCourses, new Comparator<Course>() {
            @Override
            public int compare(Course c1, Course c2) {
                if (c1.getCourseYear().equals(c2.getCourseYear())) {
                    //if years are the same go by quarters
                    int c1QuarterNum = 0;
                    int c2QuarterNum = 0;
                    String[] quarters = new String[] {"FA", "WI", "SP", "SS1", "SS2", "SSS"};
                    for (int i = 0; i < quarters.length; i++) {
                        if (c1.getQuarter().equals(quarters[i])) {
                            c1QuarterNum = i;
                        }
                        if (c2.getQuarter().equals(quarters[i])) {
                            c2QuarterNum = i;
                        }
                    }
                    return c1QuarterNum - c2QuarterNum;

                } else {
                    //if years not the same can just compare that
                    return Integer.valueOf(c1.getCourseYear()) - Integer.valueOf(c2.getCourseYear());
                }
            }
        });

        //allCourses sorted by most recent now. Loop through them, get each personID for each course, and sort person accordingly?
        List<Person> newPersonsList = new ArrayList<Person>();
        for (Course course : allCourses) {
            int thisPersonId = course.getPersonId();
            Person toInsert = db.personsDao().get(thisPersonId); //WILL NOT WORK IF CAN'T RETRIEVE A PERSON BY PERSON ID SO NEED TO FIX get!
            newPersonsList.add(toInsert);
        }
        persons.clear();
        persons.addAll(newPersonsList);

    }


    public void sortBySmallClasses(List<Person> persons){
        List<Course> allCourses = db.coursesDao().getAllCourses();
        Collections.sort(allCourses, new Comparator<Course>() {
            @Override
            public int compare(Course c1, Course c2) {

                int c1ClassSize = 0;
                int c2ClassSize = 0;
                String[] quarters = new String[]{"Tiny(<40)", "Small(40-75)", "Medium(75-150)", "Large(150-250)", "Huge(250-400)", "Gigantic(400+)"};
                for (int i = 0; i < quarters.length; i++) {
                    if (c1.getQuarter().equals(quarters[i])) {
                        c1ClassSize = i;
                    }
                    if (c2.getQuarter().equals(quarters[i])) {
                        c2ClassSize = i;
                    }
                }
                return c1ClassSize - c2ClassSize;
            }
            });
        //sorted all courses by class size, so now sort people according to those classes
        List<Person> newPersonsList = new ArrayList<Person>();
        for (Course course : allCourses) {
            int thisPersonId = course.getPersonId();
            Person toInsert = db.personsDao().get(thisPersonId); //WILL NOT WORK IF CAN'T RETRIEVE A PERSON BY PERSON ID SO NEED TO FIX get!
            newPersonsList.add(toInsert);
        }
        persons.clear();
        persons.addAll(newPersonsList);

        }

    public void sortByThisQuarterOnly(List<Person> persons) {
        List<Course> allCourses = db.coursesDao().getAllCourses(); //Assuming getAllCourses() works
        List<Person> newPersonsList = new ArrayList<Person>();
        for (Course course : allCourses) {
            if (course.getCourseYear().equals("2022") && course.getQuarter().equals("WI")) {
                int thisPersonId = course.getPersonId();
                Person toInsert = db.personsDao().get(thisPersonId); //WILL NOT WORK IF CAN'T RETRIEVE A PERSON BY PERSON ID SO NEED TO FIX get!
                newPersonsList.add(toInsert);
            }
        }
        persons.clear(); //hopefully maintains the sorted order by common courses
        persons.addAll(newPersonsList);

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

    //for filtering students to show only those favorited
    public void onCheckBoxClicked(View view) {
        CompoundButton filterByFavCheckbox = (CompoundButton) findViewById(R.id.filter_by_fav_box);
       //only if checked to on position
        if (filterByFavCheckbox.isChecked()){
            //delete all the student views, grab only the subset of students that are favorites, then update recyclerview
            List<Person> persons = db.personsDao().getAllPeople();
            List<Person> favStudents = new ArrayList<Person>();
            for (Person person : persons){
                if (person.getIsFavorite()){
                    favStudents.add(person);
                }
            }
            //somehow reset the recyclerview so its displaying favStudents instead
            persons.clear();
            persons.addAll(favStudents);

            studentsViewAdapter.notifyDataSetChanged();//should change recyclerview?
        }
    }







}