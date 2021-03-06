package com.benlau.bofteam1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

//import com.google.android.gms.nearby.Nearby;
//import com.google.android.gms.nearby.messages.Message;
//import com.google.android.gms.nearby.messages.MessageListener;
//import com.google.android.gms.nearby.messages.MessagesClient;
//import com.google.android.gms.nearby.messages.MessagesOptions;
//import com.google.android.gms.nearby.messages.NearbyPermissions;


public class MainActivity extends AppCompatActivity {
  //all Recylerview code is experimental now
  protected RecyclerView personsRecyclerView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_main);

    /* I (Mark) have chosen to not put profile activities into Main Activity
     * On this class' creation, it will launch the Profile Activity.
     * The reason is, I want to create a buffer for the Profile Activity Screen in case it ends up
     * not being the very first screen that we see; for ex: the bluetooth permission
     */
    //System.out.println("HELLO");
    //temporary functionality to launch Profile Activity as soon as Main Activity is created
    //will be modified once bluetooth permission is finalized with group
    Intent intent = new Intent(this, ProfileActivity.class);
    startActivity(intent);

  }

  /*
   * The following two methods are TEMPORARY to enable the functionality of launching the Profile
   * Creation screen through ProfileActivity.java
   */
  /*
  public void onAllowClicked(View view) {
    Intent intent = new Intent(this, ProfileActivity.class);
    startActivity(intent);
  }

  public void onCancelClicked(View view) {
  }*/

    /*
    public void onLaunchAddCourse(View view) {
      Intent switchActivityIntent = new Intent(this, UserClass.class);
      startActivity(switchActivityIntent);
    }*/
}
