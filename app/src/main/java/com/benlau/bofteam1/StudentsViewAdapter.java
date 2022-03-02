package com.benlau.bofteam1;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.benlau.bofteam1.db.Student;

import java.util.List;

public class StudentsViewAdapter extends RecyclerView.Adapter<StudentsViewAdapter.ViewHolder> {
    private final List<Student> students;
    private String userUUID = "";

    public StudentsViewAdapter(List<Student> students){
        super();
        this.students = students;
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

    public void addStudent(Student student){
        this.students.add(student);
        this.notifyItemInserted(this.students.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView studentNameView;
        private final TextView courseCountView;
        private final ImageView studentUrl;
        private Student student;

        ViewHolder(View itemView) {
            super(itemView);
            this.studentNameView = itemView.findViewById(R.id.student_row_name);
            this.courseCountView = itemView.findViewById(R.id.num_common);
            this.studentUrl = itemView.findViewById(R.id.image_url);

            itemView.setOnClickListener(this);
        }

        public void setStudent(Student student) {
            this.student = student;
            this.studentNameView.setText(student.getStudentName());
        }

        public void setCourseCount(Student student) {
            this.courseCountView.setText(student.getNumCommonCourses());
        }

        public void setStudentUrl(Student student) {
            LoadImage loadImage = new LoadImage(this.studentUrl);
            loadImage.execute(student.getPhotoUrl());
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, PersonFileDetailActivity.class);
            intent.putExtra("person_name", this.student.getStudentName());
            intent.putExtra("url", this.student.getPhotoUrl());
            intent.putExtra("userUUID", StudentsViewAdapter.this.students.get(0).getUUID());
            intent.putExtra("newStudentUUID", this.student.getUUID());
            context.startActivity(intent);
        }
    }
}
