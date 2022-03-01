package com.benlau.bofteam1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benlau.bofteam1.db.AppDatabase;
import com.benlau.bofteam1.db.Course;
import com.benlau.bofteam1.db.IPerson;
import com.benlau.bofteam1.db.Person;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentsViewAdapter extends RecyclerView.Adapter<StudentsViewAdapter.ViewHolder> {
    private final List<Person> students;
    boolean firstTime = true;


    public StudentsViewAdapter(List<Person> students){
        super();
        this.students = students;
        Collections.sort(students, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                if(lhs.getPersonId() == 1){return 0;} //no matter what rhs < lhs is, return rhs
                if (rhs.getPersonId()==1){return 0;} //no matter what rhs < lhs is, return lhs
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return Integer.valueOf(rhs.getCommonCourses()) < Integer.valueOf(lhs.getCommonCourses()) ? -1 : (Integer.valueOf(rhs.getCommonCourses()) > Integer.valueOf(lhs.getCommonCourses())) ? 1 : 0;
            }
        });




    }
    @NonNull
    @Override
    public StudentsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsViewAdapter.ViewHolder holder, int position){
        if(position!= 0) {
            holder.setStudent(students.get(position));
            holder.setStudentUrl(students.get(position));
            holder.setCourseCount(students.get(position));
        }
    }

    @Override
    public int getItemCount(){
        return this.students.size();
    }

    public void addStudent(Person student){

        this.students.add(student);
        this.notifyItemInserted(this.students.size() - 1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView studentNameView;
        private final TextView courseCountView;
        private final ImageView studentUrl;
        private Person student;

        ViewHolder(View itemView) {
            super(itemView);
            this.studentNameView = itemView.findViewById(R.id.student_row_name);
            this.courseCountView = itemView.findViewById(R.id.num_common);
            this.studentUrl = itemView.findViewById(R.id.image_url);

            itemView.setOnClickListener(this);
        }

        public void setStudent(Person student) {
            this.student = student;
            this.studentNameView.setText(student.getPersonName());
        }

        public void setCourseCount(Person student) {
            this.courseCountView.setText(student.getCommonCourses());
        }

        public void setStudentUrl(Person student) {
            LoadImage loadImage = new LoadImage(this.studentUrl);
            loadImage.execute(student.getPhotoUrl());
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, PersonFileDetailActivity.class);
            intent.putExtra("person_name", this.student.getPersonName());
            intent.putExtra("url", this.student.getPhotoUrl());
            context.startActivity(intent);
        }
    }
}
