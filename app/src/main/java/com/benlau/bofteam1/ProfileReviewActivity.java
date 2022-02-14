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
import com.benlau.bofteam1.db.Person;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ProfileReviewActivity extends AppCompatActivity {

    ImageView URL_pic;
    Button load;
    TextView textView;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_review);
        URL_pic = findViewById(R.id.test_url);
        LoadImage loadImage = new LoadImage(URL_pic);
        loadImage.execute(getProfileURL());

        TextView PreferredName = findViewById(R.id.PreferredName);
        PreferredName.setText(getPreferredName());
        db = AppDatabase.singleton(getApplicationContext());
    }

    /*
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

    public String getPreferredName() {
        SharedPreferences preferences = getSharedPreferences("Profile", MODE_PRIVATE);
        return preferences.getString("name", "");
    }

    /*
     * Function launches the UserClass Activity which allows the user to input class history
     */
    public void onConfirmClicked(View v) {
        //creating a new person when profile is made and adding them to db
        //hardcoding common courses to 0
        //empty list of courses for courses in common with itself, wont' make a difference
        Person newPerson = new Person(this.getPreferredName(),this.getProfileURL(), "0");
        db.personsDao().insert(newPerson);
        //then retrieve this person with db.personsDao().get(0)
        //retrive this person's list of courses with db.coursesDao().getCoursesForPerson(0)
        Intent intent = new Intent(this, CourseHistoryActivity.class);
        startActivity(intent);
    }
}