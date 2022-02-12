package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ProfileReview extends AppCompatActivity {

    ImageView URL_pic;
    Button load;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_review);
        /*
        URL_pic = findViewById(R.id.test_url);

        load = findViewById(R.id.button);
        textView = findViewById(R.id.editTextTextPersonName);


        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlLink = textView.getText().toString();

                LoadImage loadimage = new LoadImage(URL_pic);
                loadimage.execute(urlLink);
            }
        });
        */

        URL_pic = findViewById(R.id.test_url);
        LoadImage loadImage = new LoadImage(URL_pic);
        loadImage.execute(getProfileURL());

        TextView PreferredName = findViewById(R.id.PreferredName);
        PreferredName.setText(getPreferredName());

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
        //UNCOMMENT WHEN READY TO INTEGRATE!!!!!!
        //Intent intent = new Intent(this, UserClass.class);
        //startActivity(intent);
    }
}