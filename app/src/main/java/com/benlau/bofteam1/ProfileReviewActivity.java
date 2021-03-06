package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.benlau.bofteam1.db.AppDatabase;
import com.benlau.bofteam1.db.Student;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class ProfileReviewActivity extends AppCompatActivity {

    ImageView URL_pic;
    Button load;
    TextView textView;
    AppDatabase db;

    /**
     * Method that creates the ProfileReview Activity.  Meant to provide a preview page for the
     * User's Profile so that they can determine whether or not to proceed with their profile
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_review);
        URL_pic = findViewById(R.id.test_url);
        LoadImage loadImage = new LoadImage(URL_pic);
        loadImage.execute(getProfileURL());

        TextView PreferredName = findViewById(R.id.PreferredName);
        PreferredName.setText(getPreferredName());
        //initializes database to finalize storage of User's Profile Informatoin
        db = AppDatabase.singleton(getApplicationContext());
    }

    /**
     * Function gets the ProfileURL that's saved via SharedPreferences
     *
     * @return - String representing ProfileURL
     */
    public String getProfileURL() {
        SharedPreferences preferences = getSharedPreferences("Profile", MODE_PRIVATE);
        /* for QUICK TESTING purposes
        TextView textView = findViewById(R.id.textView);
        textView.setText(preferences.getString("photoURL", ""));
        //load from the hashmap
        Log.d("Hello", preferences.getString("photoURL", ""));
         */
        return preferences.getString("photoURL", "");
    }

    /**
     * Method that reads the User's name via SharedPreferences
     *
     * @return - String representing User's name
     */
    public String getPreferredName() {
        SharedPreferences preferences = getSharedPreferences("Profile", MODE_PRIVATE);
        return preferences.getString("name", "");
    }

    /**
     * Function launches the UserClass Activity which allows the user to input class history
     *
     * @param - View v
     */
    public void onConfirmClicked(View v) {
        //creating a new person when profile is made and adding them to db
        //hardcoding common courses to 0, will  get updated via a setter() later
        //empty list of courses for courses in common with itself, won't make a difference
        String uniqueID = UUID.randomUUID().toString();
        Student newStudent = new Student(this.getPreferredName(),this.getProfileURL(), "0", uniqueID);
        db.studentsDao().insert(newStudent);
        //then retrieve this person with db.personsDao().get(0)
        //retrive this person's list of courses with db.coursesDao().getCoursesForPerson(0)
        Intent intent = new Intent(this, CourseHistoryActivity.class);
        intent.putExtra("UUID", uniqueID);
        startActivity(intent);
    }
}