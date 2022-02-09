package com.benlau.bofteam1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentsViewAdapter extends RecyclerView.Adapter<StudentsViewAdapter.ViewHolder> {
    private final DummyStudent[] students;

    public StudentsViewAdapter(DummyStudent[] students){
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
        holder.setStudent(students[position]);
        //holder.setUrl(students[position]);
        holder.setCourseCount(students[position]);
    }

    @Override
    public int getItemCount(){
        return this.students.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView studentNameView;
        private final TextView courseCountView;
        private final ImageView studentUrl;
        private DummyStudent student;

        ViewHolder(View itemView){
            super(itemView);
            this.studentNameView = itemView.findViewById(R.id.student_row_name);
            this.courseCountView = itemView.findViewById(R.id.num_common);
            this.studentUrl = itemView.findViewById(R.id.image_url);

            itemView.setOnClickListener(this);
        }

        public void setStudent(DummyStudent student){
            this.student = student;
            this.studentNameView.setText(student.name);
        }

        public void setCourseCount(DummyStudent student){
            this.courseCountView.setText(student.numCommon);
        }

        // uncomment when integrating with story 4
        @Override
        public void onClick(View view){
            Context context = view.getContext();
           // Intent intent = new Intent(context, StudentDetailActivity.class)
        }
    }
}
