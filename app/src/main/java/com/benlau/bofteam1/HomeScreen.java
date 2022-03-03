package com.benlau.bofteam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.benlau.bofteam1.db.AppDatabase;
import com.benlau.bofteam1.db.Course;
import com.benlau.bofteam1.db.Student;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

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
    List<Student> students;

    private AppDatabase db;
    private MessageListener messageListener;
    private MessageListener realListener;
    private static final String TAG = "HERE I AM FOR VISIBILITY IN THE LOG";
    private String received = "";
    private boolean wave = false;
    private String userUUID;



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
        students = db.studentsDao().getAllStudents();

        studentsRecyclerView = findViewById(R.id.student_view);
        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);


        studentsViewAdapter = new StudentsViewAdapter(students);
        studentsRecyclerView.setAdapter(studentsViewAdapter);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userUUID = extras.getString("UUID");
        }

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
                               students = sortByRecent(students);
                               studentsViewAdapter.notifyDataSetChanged();//not sure if this helps
                               studentsRecyclerView.setAdapter(new StudentsViewAdapter(students));
                                break;
                        case "prioritize small classes":
                               students = sortBySmallClasses(students);
                               studentsViewAdapter.notifyDataSetChanged();
                            studentsRecyclerView.setAdapter(new StudentsViewAdapter(students));
                                break;
                        case "this quarter only":
                                //for now dummy value for this quarter is 2022 WI
                                students = sortByThisQuarterOnly(students);
                                studentsViewAdapter.notifyDataSetChanged();
                            studentsRecyclerView.setAdapter(new StudentsViewAdapter(students));
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
    public List<Student> sortByRecent(List<Student> persons){
        List<Course> allCourses = db.coursesDao().getAllCourses();
        //calculate the recency score, set it and add to newPersonList, which will sort it in descending order
        List<Student> newStudentsList = new ArrayList<Student>();
        for (Student student : students) {
            int recencyScore = calculateRecencyScoreFor(student);
            student.setRecencyScore(recencyScore);
            newStudentsList.add(student);
            //no specification for handling ties, just do it arbitrarily
        }
        Collections.sort(newStudentsList, new Comparator<Student>() {
            @Override
            public int compare(Student lhs, Student rhs) {
                if(lhs.getUUID().equals(userUUID)){return 0;} //no matter what rhs < lhs is, return rhs, not sure if this works
                if (rhs.getUUID().equals(userUUID)){return 0;} //no matter what rhs < lhs is, return lhs, not sure if this works
                return rhs.getRecencyScore() < lhs.getRecencyScore() ? -1 : rhs.getRecencyScore() > lhs.getRecencyScore() ? 1 : 0;
            }
        });
        students.clear();
        students.addAll(newStudentsList);
        return students;
    }


    public List<Student> sortBySmallClasses(List<Student> students){

        List<Course> allCourses = db.coursesDao().getAllCourses();
        //calculate the classsizeweight for each person and set it, add it to newPersonsList which sorts it in descending order
        List<Student> newStudentsList = new ArrayList<Student>();
        for (Student student : students) {
            double classSizeWeight = calculateClassSizeWeightFor(student);
            student.setClassSizeWeight(classSizeWeight);
            newStudentsList.add(student);
            //no specification for handling ties, just do it arbitrarily
        }

        Collections.sort(newStudentsList, new Comparator<Student>() {
            @Override
            public int compare(Student lhs, Student rhs) {
                if(lhs.getUUID().equals(userUUID)){return 0;} //not sure what this does but it works
                if (rhs.getUUID().equals(userUUID)){return 0;} //not sure what this does but it works
                return rhs.getClassSizeWeight() < lhs.getClassSizeWeight() ? 1 : rhs.getClassSizeWeight() > lhs.getClassSizeWeight() ? -1 : 0;
            }
        });
        students.clear();
        students.addAll(newStudentsList);
        return students;
        }

    public List<Student> sortByThisQuarterOnly(List<Student> students) {
        //add people that match 2022 WI but sort otherwise by common courses
        List<Student> allStudents = db.studentsDao().getAllStudents();
        List<Student> newStudentsList = new ArrayList<Student>();
        // newPersonsList.add(db.personsDao().get(1)); //have to add some random person bc according to studentViewAdapter person in position 0 is not shown
        for (Student student : allStudents){
            List<Course> allCoursesForStudent = db.coursesDao().getCoursesForStudent(student.getUUID());//ASSUMING GETPERSONID works
            for (Course course : allCoursesForStudent){
                if (course.getYear().equals("2022") && course.getQuarter().equals("WI")){
                    if (!student.getUUID().equals(this.userUUID)) {
                        newStudentsList.add(student);
                    }
                }
            }
        }
        students.clear(); //hopefully maintains the sorted order by common courses
        students.addAll(newStudentsList);
        return students;

    }


    /*
     * Method for calculating the class weights for a Person object
     * Assumes that getPersonId() returns a functional, unique Id,
     * can be replaced by Mark's new db method for getting person Id
     *
     */
    public double calculateClassSizeWeightFor(Student student) {
        List<Course> allCourses = db.coursesDao().getCoursesForStudent(student.getUUID());
        double sumValue = 0;
        for (Course course : allCourses) {
            switch (course.getCourseSize()) {
                case "Tiny":
                    sumValue+=1.00;
                    break;
                case "Small":
                    sumValue+=0.33;
                    break;
                case "Medium":
                    sumValue+=0.18;
                    break;
                case "Large":
                    sumValue+=0.10;
                    break;
                case "Huge":
                    sumValue+=0.06;
                    break;
                case "Gigantic":
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
    public int calculateRecencyScoreFor(Student student) {
        List<Course> allCourses = db.coursesDao().getCoursesForStudent(student.getUUID());
        int sumValue = 0;
        for (Course course : allCourses) {
            switch (course.getYear()) {
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
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            received = extras.getString("Information");
            wave = extras.getBoolean("wave");
        }
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
        messageListener = new FakedMessageListener(this.realListener, 3, received);

        Nearby.getMessagesClient(this).subscribe(messageListener);
        Intent intent = new Intent(this, FakedMessageListenerActivity.class);
        intent.putExtra("UUID", userUUID);
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
            List<Student> persons = db.studentsDao().getAllStudents();
            List<Student> favStudents = new ArrayList<Student>();
            //favStudents.add(db.studentsDao().get(1));//have to insert one person beforehand bc of db bug, should be fixed by Mark's updates?
            for (Student person : persons){
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
            List<Student> allStudents = db.studentsDao().getAllStudents();
            //everytime new adapter created the list should be sorted by common courses
            studentsViewAdapter.notifyDataSetChanged();
            studentsRecyclerView.setAdapter(new StudentsViewAdapter(allStudents));
        }

    }







    public void onStopClicked(View view) {
        Nearby.getMessagesClient(this).unsubscribe(messageListener);
    }

    public void onStop() {
        super.onStop();
    }

    public void onStart() {
        super.onStart();
    }

}
