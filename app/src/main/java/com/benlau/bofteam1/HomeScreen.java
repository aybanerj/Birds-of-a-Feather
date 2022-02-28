package com.benlau.bofteam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.benlau.bofteam1.db.AppDatabase;
import com.benlau.bofteam1.db.Course;
import com.benlau.bofteam1.db.Student;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.List;

public class HomeScreen extends AppCompatActivity {
    protected RecyclerView studentsRecyclerView;
    protected RecyclerView.LayoutManager studentsLayoutManager;
    protected StudentsViewAdapter studentsViewAdapter;
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

        db = AppDatabase.getDatabase(getApplicationContext());
        List<Student> students = db.studentsDao().getAllStudents();

        studentsRecyclerView = findViewById(R.id.student_view);
        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);

        studentsViewAdapter = new StudentsViewAdapter(students);
        studentsRecyclerView.setAdapter(studentsViewAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userUUID = extras.getString("UUID");
        }
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