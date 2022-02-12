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

import com.benlau.bofteam1.db.IPerson;
import com.benlau.bofteam1.db.Person;

import java.util.List;

public class StudentsViewAdapter extends RecyclerView.Adapter<StudentsViewAdapter.ViewHolder> {
    private final List<Person> students;

    public StudentsViewAdapter(List<Person> students){
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
        holder.setStudent(students.get(position));
        holder.setStudentUrl(students.get(position));
        holder.setCourseCount(students.get(position));
    }

    @Override
    public int getItemCount(){
        return this.students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView studentNameView;
        private final TextView courseCountView;
        private final ImageView studentUrl;
        private Person student;

        ViewHolder(View itemView){
            super(itemView);
            this.studentNameView = itemView.findViewById(R.id.student_row_name);
            this.courseCountView = itemView.findViewById(R.id.num_common);
            this.studentUrl = itemView.findViewById(R.id.image_url);

            itemView.setOnClickListener(this);
        }

        public void setStudent(Person student){
            this.student = student;
            this.studentNameView.setText(student.getName());
        }

        public void setCourseCount(Person student){
            this.courseCountView.setText(student.getCommonCourses());
        }

        public void setStudentUrl(Person student){
            LoadImage loadImage = new LoadImage(this.studentUrl);
            loadImage.execute(student.getPhotoUrl());
        }

        // uncomment when integrating with story 4
        @Override
        public void onClick(View view){
            Context context = view.getContext();
           // Intent intent = new Intent(context, StudentDetailActivity.class)
        }
    }
}
