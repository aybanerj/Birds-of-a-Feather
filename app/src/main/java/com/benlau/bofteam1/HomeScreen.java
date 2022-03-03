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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        studentsRecyclerView = findViewById(R.id.student_view);
        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);


        studentsViewAdapter = new StudentsViewAdapter(persons);
        studentsRecyclerView.setAdapter(studentsViewAdapter);

        //since spinner click listener will be triggered when onCreate starts and when user selects it,
        //onCreateCall flag defines if its still initializing the homescreen, in which case no sorting
        sortingDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (onCreateCall) {
                    onCreateCall = false;
                }
                else {
                    String sortingOption = (String)adapterView.getItemAtPosition(i);//can you cast it to string?
                    switch(sortingOption){
                        case "prioritize recent":
                               persons = sortByRecent(persons);
                               studentsViewAdapter.notifyDataSetChanged();//not sure if this helps
                               studentsRecyclerView.setAdapter(new StudentsViewAdapter(persons));
                                break;
                        case "prioritize small classes":
                               persons = sortBySmallClasses(persons);
                               studentsViewAdapter.notifyDataSetChanged();
                            studentsRecyclerView.setAdapter(new StudentsViewAdapter(persons));
                                break;
                        case "this quarter only":
                                //for now dummy value for this quarter is 2022 WI
                                persons = sortByThisQuarterOnly(persons);
                                studentsViewAdapter.notifyDataSetChanged();
                            studentsRecyclerView.setAdapter(new StudentsViewAdapter(persons));
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
    public List<Person> sortByRecent(List<Person> persons){
        List<Course> allCourses = db.coursesDao().getAllCourses();
        //calculate the recency score, set it and add to newPersonList, which will sort it in descending order
        List<Person> newPersonsList = new ArrayList<Person>();
        for (Person person : persons) {
            int recencyScore = calculateRecencyScoreFor(person);
            person.setRecencyScore(recencyScore);
            newPersonsList.add(person);
            //no specification for handling ties, just do it arbitrarily
        }
        Collections.sort(newPersonsList, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                if(lhs.getPersonId() == 1){return 0;} //no matter what rhs < lhs is, return rhs, not sure if this works
                if (rhs.getPersonId()==1){return 0;} //no matter what rhs < lhs is, return lhs, not sure if this works
                return rhs.getRecencyScore() < lhs.getRecencyScore() ? -1 : rhs.getRecencyScore() > lhs.getRecencyScore() ? 1 : 0;
            }
        });
        persons.clear();
        persons.addAll(newPersonsList);
        return persons;
    }


    public List<Person> sortBySmallClasses(List<Person> persons){

        List<Course> allCourses = db.coursesDao().getAllCourses();
        //calculate the classsizeweight for each person and set it, add it to newPersonsList which sorts it in descending order
        List<Person> newPersonsList = new ArrayList<Person>();
        for (Person person : persons) {
            double classSizeWeight = calculateClassSizeWeightFor(person);
            person.setClassSizeWeight(classSizeWeight);
            newPersonsList.add(person);
            //no specification for handling ties, just do it arbitrarily
        }

        Collections.sort(newPersonsList, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                if(lhs.getPersonId() == 1){return 0;} //not sure what this does but it works
                if (rhs.getPersonId()==1){return 0;} //not sure what this does but it works
                return rhs.getClassSizeWeight() < lhs.getClassSizeWeight() ? 1 : rhs.getClassSizeWeight() > lhs.getClassSizeWeight() ? -1 : 0;
            }
        });
        persons.clear();
        persons.addAll(newPersonsList);
        return persons;
        }

    public List<Person> sortByThisQuarterOnly(List<Person> persons) {
        //add people that match 2022 WI but sort otherwise by common courses
        List<Person> allPeople = db.personsDao().getAllPeople();
        List<Person> newPersonsList = new ArrayList<Person>();
        // newPersonsList.add(db.personsDao().get(1)); //have to add some random person bc according to studentViewAdapter person in position 0 is not shown
        for (Person person : allPeople){
            List<Course> allCoursesForPerson = db.coursesDao().getCoursesForPerson(person.getPersonId());//ASSUMING GETPERSONID works
            for (Course course : allCoursesForPerson){
                if (course.getCourseYear().equals("2022") && course.getQuarter().equals("WI")){
                    if (person.getPersonId()!=1) {
                        newPersonsList.add(person);
                    }
                }
            }
        }
        persons.clear(); //hopefully maintains the sorted order by common courses
        persons.addAll(newPersonsList);
        return persons;

    }


    /*
     * Method for calculating the class weights for a Person object
     * Assumes that getPersonId() returns a functional, unique Id,
     * can be replaced by Mark's new db method for getting person Id
     *
     */
    public double calculateClassSizeWeightFor(Person person) {
        List<Course> allCourses = db.coursesDao().getCoursesForPerson(person.getPersonId());
        double sumValue = 0;
        for (Course course : allCourses) {
            switch (course.getClassSize()) {
                case "Tiny(<40)":
                    sumValue+=1.00;
                    break;
                case "Small(40-75)":
                    sumValue+=0.33;
                    break;
                case "Medium(75-150)":
                    sumValue+=0.18;
                    break;
                case "Large(150-250)":
                    sumValue+=0.10;
                    break;
                case "Huge(250-400)":
                    sumValue+=0.06;
                    break;
                case "Gigantic(400+)":
                    sumValue+=0.03;
                    break;
                default:
                    break;

            }
        }
        return sumValue;
    }
    /*
     * Method for calculating weights based on how recent a class was taken
     * Anything in 2022 and 2021 FA is score of 5,
     * 2021 SS1 SS2 SSS is 4
     * 2021 SP is 3
     * 2021 WI 2
     * Anything before that is 1
     * Uses original getPersonId from old db, can be replaced with Mark's db methods
     *
     */
    public int calculateRecencyScoreFor(Person person) {
        List<Course> allCourses = db.coursesDao().getCoursesForPerson(person.getPersonId());
        int sumValue = 0;
        for (Course course : allCourses) {
            switch (course.getCourseYear()) {
                case "2022":
                    sumValue += 5;
                    break;
                case "2021":
                    if (course.getQuarter().equals("FA")) {
                        sumValue += 5;
                        break;
                    }
                    if (course.getQuarter().equals("SS1") || course.getQuarter().equals("SS2") || course.getQuarter().equals("SSS")) {
                        sumValue += 4;
                        break;
                    }
                    if (course.getQuarter().equals("WI")) {
                        sumValue += 3;
                        break;
                    }
                case "2020":
                    if (course.getQuarter().equals("FA")) {
                        sumValue += 2;
                        break;
                    }
                    //anything before 2020 FA is a score of 1
                    sumValue += 1;
                    break;
                default:
                    sumValue += 1;
                    break;
            }
        }
        return sumValue;
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

    /*
      For filtering students to only show those that are favorited
     */
    public void onCheckBoxClicked(View view) {
        CompoundButton filterByFavCheckbox = (CompoundButton) findViewById(R.id.filter_by_fav_box);
       //only if checked to on position
        if (filterByFavCheckbox.isChecked()){
            //grab only the subset of students that are favorites, then update recyclerview
            List<Person> persons = db.personsDao().getAllPeople();
            List<Person> favStudents = new ArrayList<Person>();
            favStudents.add(db.personsDao().get(1));//have to insert one person beforehand bc of db bug, should be fixed by Mark's updates?
            for (Person person : persons){
            //assumes some people will be favorited using setIsFavorite in some other activity
                if (person.getIsFavorite()){

                    favStudents.add(person);
                }
            }

            persons.clear();
            persons.addAll(favStudents);
            studentsViewAdapter.notifyDataSetChanged();
            studentsRecyclerView.setAdapter(new StudentsViewAdapter(persons));

        }
        else {
            List<Person> allPeople = db.personsDao().getAllPeople();
            //everytime new adapter created the list should be sorted by common courses
            studentsViewAdapter.notifyDataSetChanged();
            studentsRecyclerView.setAdapter(new StudentsViewAdapter(allPeople));
        }

    }







}