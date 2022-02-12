//package com.benlau.bofteam1;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.benlau.bofteam1.db.AppDatabase;
//import com.benlau.bofteam1.db.Course;
//import com.google.android.gms.nearby.Nearby;
//import com.google.android.gms.nearby.messages.MessageListener;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//public class FakedMessageListenerActivity extends AppCompatActivity {
//    private MessageListener messageListener;
//    private AppDatabase db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        //from the start button on the home screen
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_faked_message_listener);
//    }
//
//    public String joinString(ArrayList<String> s) {
//        String string = "";
//        for (int i = 0; i < s.size(); i++) {
//            string = string + s.get(i);
//        }
//        return string;
//    }
//
//    /**
//     * Method to head back to the HomeScreenActivity
//     * @param view
//     */
//    public void onEnterClicked(View view) {
//        EditText receivedProfile = findViewById(R.id.NearbyMessageMockedTextbox);
//        String receivedString = receivedProfile.getText().toString();
//        /*
//        read in data entered into a textbox formatted in a csv file format via file I/O
//        note the commas to determine name/URL/classes
//        separate and place into string arrayList
//        split the string here until every part is an element in an arrayList
//        insert into database/add this person in the same way that you added the others
//         */
//        ArrayList<String> list = new ArrayList<>(Arrays.asList(receivedString.split("\n\n")));
//        receivedString = this.joinString(list);
//        list = new ArrayList<String>(Arrays.asList(receivedString.split(",")));
//        list.removeAll(Collections.singleton(""));
//        receivedProfile.setText("");
//
//        //HMMM maybe gotta modify the database
//
//        /*
//        for the ArrayList
//        1st elem 0: Name
//        2nd elem 1: Photo URL
//        3rd-6th elem 2-5: Year, Quarter, Course, Number
//        7th-10th elem 6-9: Year, Quarter, Course, Number
//        ...
//        */
//        int newCourseId = db.coursesDao().maxId() + 1;
//        Course newCourse;
//        //assuming list size at least 2
//        for (int i = 2; i < list.size()-4; i++) {
//            newCourse = new Course(newCourseId, list.get(i), list.get(i+1), list.get(i+2), list.get(i+3));
//            db.coursesDao().insert(newCourse);
//        }
//    }
//
//
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Nearby.getMessagesClient(this).subscribe(messageListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Nearby.getMessagesClient(this).unsubscribe(messageListener);
//    }
//
//
//}