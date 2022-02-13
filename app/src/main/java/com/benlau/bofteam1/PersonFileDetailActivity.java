package com.benlau.bofteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonFileDetailActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView URL_pic;
    private String url;
    private ListView l;
    private String name;
    private ArrayList<String> course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_file_detail);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        name = intent.getStringExtra("person_name");
        // todo: parse courselist into arraylist of strings
        course = (ArrayList<String>) getIntent().getSerializableExtra("common_courses");


        URL_pic = findViewById(R.id.image_url);
        LoadImage loadimage = new LoadImage(URL_pic);
        loadimage.execute(url);

        textView = findViewById(R.id.textView);
        textView.setText(name);

        l = findViewById(R.id.list);
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(this, R.layout.student_detail_row, course);
        l.setAdapter(arr);
    }
}