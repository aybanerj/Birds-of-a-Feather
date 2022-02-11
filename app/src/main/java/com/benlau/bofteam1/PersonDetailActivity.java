/*

 too many missing parts to implement this right now

package com.benlau.bofteam1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.benlau.bofteam1.db.AppDatabase;
import com.benlau.bofteam1.db.Course;
import com.benlau.bofteam1.db.Person;

public class PersonDetailActivity extends AppCompatActivity {

    private AppDatabase db;
    private Person person;

    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private CoursesViewAdapter coursesViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //need the activity that displays people's thumbnails
        setContentView(R.layout.activity_person_detail);

        Intent intent = getIntent();
        int personId = intent.getIntExtra("person_id", 0);

        db = AppDatabase.singleton(this);
        person = db.personsDao().get(personId);
        List<Course> notes = db.coursesDao().getCoursesForPerson(personId);

        // Set the title with the person.
        setTitle(person.getName());

        // Set up the recycler view to show our database contents.
        //need a courses_view within activity_person_detail.xml
        coursesRecyclerView = findViewById(R.id.notes_view);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);

        coursesViewAdapter = new CoursesViewAdapter(courses, (course) -> {
            db.coursesDao().delete(course);
        });
        coursesRecyclerView.setAdapter(coursesViewAdapter);

    }

    public void onAddNoteClicked(View view) {
        int personId = person.getPersonId();
        TextView newNoteTextView = findViewById(R.id.new_note_textview);
        String newNoteText = newNoteTextView.getText().toString();

        Note newNote = new Note(personId, newNoteText);
        db.notesDao().insert(newNote);

        notesViewAdapter.addNote(newNote);
    }

    public void onGoBackClicked(View view) {
        finish();
    }
}


}
*/
