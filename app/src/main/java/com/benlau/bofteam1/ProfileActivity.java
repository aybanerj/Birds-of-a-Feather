package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {

    //notes:
    //Intents here to launch the profile page activity
    //don't close previous activities, they can get reached by pressing back button

    //a boolean here to check for initial setup to determine whether or not to autofill from Google
    //this is so that when you go back to edit your profile, it does not autofill from Google every time
    //Let me (Mark) know if this functionality is undesirable - that is, you want autofill every time
    boolean initialSetup = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //print statement to verify that this activity is being created
        System.out.println("Profile Activity Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //load data here
        if (initialSetup == true) {
            //autofills Google Login information
        }
        //load profile from hashmap
    }

    /*
     * Data entered in the name and photo URL field MUST be saved somewhere, like to a hashmap in Lab4
     * When revisiting the profile later, this data MUST be loaded, for ex:
     * a loadProfile() that implements reading from the hashmap
     * a saveProfile() that implements writing to the hashmap
     * a onDestroy() to Destroy this activity
     * a exitAndSave() to SAVE and EXIT activity while launching the previous activity
     * note: dunno if specifying the intent of the previous activity and launching it as you would
     * a new activity is necessary, but will implement this way to be safe at first
     */


    /*
     * Function that launches the (insert name of activity for PROFILE PREVIEW) screen using an intent
     * @param View - a view representing my political and social viewpoints (gonna read the lab again)
     */
    public void onSubmitClicked(View view) {
        //Intent intent = new Intent(/* the class for the PROFILE PREVIEW screen goes here */);
        //startActivity(intent);
    }
}