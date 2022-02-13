package com.benlau.bofteam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

public class ProfileActivity extends AppCompatActivity {

    //notes:
    //Intents here to launch the profile page activity
    //don't close previous activities, they can get reached by pressing back button

    //a boolean here to check for initial setup to determine whether or not to autofill from Google
    //this is so that when you go back to edit your profile, it does not autofill from Google every time
    //Let me (Mark) know if this functionality is undesirable - that is, you want autofill every time
    boolean initialSetup = true;
    private MessageListener messageListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //print statement to verify that this activity is being created
        //System.out.println("Profile Activity Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //IN THE SPIRIT OF MOCKING
        //Here we have this line whose ONLY purpose is to display the bluetooth permissions
        //dialogue box.  It is PURELY DECORATION!!!
        //MessageListener realListener = new MessageListener() {};
        //Nearby.getMessagesClient(this).subscribe(messageListener);


        //load data here
        if (initialSetup == true) {
            //autofills Google Login information
        }
        //load profile from hashmap
        this.loadProfile();
    }


    /*
     * Data entered in the name and photo URL field MUST be saved somewhere, like to a hashmap in Lab4
     * When revisiting the profile later, this data MUST be loaded, for ex:
     * a loadProfile() that implements reading from the hashmap
     * a saveProfile() that implements writing to the hashmap
     * a onDestroy() to Destroy this activity
     * a exitAndSave() *I named it Submit* to SAVE and EXIT activity while launching the previous activity
     * note: dunno if specifying the intent of the previous activity and launching it as you would
     * a new activity is necessary, but will implement this way to be safe at first
     */


    /*
     * Function that launches the (Profile Review) screen using an intent
     * and SAVES the current profile
     * @param View - a view representing my political and social viewpoints (gonna read the lab again)
     */
    public void onSubmitClicked(View view) {
        //verify the the validity of the name
        EditText nameField = findViewById(R.id.nameField);
        String name = nameField.getText().toString();
        if (name.length() > 100) {
            Utilities.showAlert(this, "Your name needs to be <= 100 characters");
            return;
        }
        if (name.length() <= 0) {
            Utilities.showAlert(this, "your name can't be empty");
            return;
        }
        String regex = "^[a-zA-Z- ]*$";
        if (!name.matches(regex)) {
            Utilities.showAlert(this, "your name can only be letters, space, and hyphens!");
            return;
        }
        //only start Profile Review Activity after verifying the validity of name
        Intent intent = new Intent(this, ProfileReview.class);
        startActivity(intent);
        this.saveProfile();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        this.saveProfile();
        //call save here

    }

    public void loadProfile() {
        SharedPreferences preferences = getSharedPreferences("Profile", MODE_PRIVATE);
        //load from the hashmap

        EditText nameField = findViewById(R.id.nameField);
        EditText photoField = findViewById(R.id.photoField);
        nameField.setText(preferences.getString("name", ""));
        photoField.setText(preferences.getString("photoURL", ""));

    }

    public void saveProfile() {
        SharedPreferences preferences = getSharedPreferences("Profile", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //write to the hashmap
        TextView nameField = findViewById(R.id.nameField);
        TextView photoField = findViewById(R.id.photoField);
        editor.putString("name",nameField.getText().toString());
        editor.putString("photoURL",photoField.getText().toString());

        editor.apply();
    }


}