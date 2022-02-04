package com.benlau.bofteam1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CoursesViewAdapter extends RecyclerView.Adapter<CoursesViewAdapter.ViewHolder> {

    @NonNull
    @Override
    public CoursesViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.course_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewAdapter.ViewHolder holder, int position){
        holder.setCourse(courses[position]);
    }

    @Override
    public int getItemCount(){
        return this.courses.length;
    }

    public static class ViewHolder extends
            RecyclerView.ViewHolder
            implements View.OnClickListener{
        private final TextView courseNameView;
        private ICourse course;

        ViewHolder(View itemView){
            super(itemView);
            this.courseNameView = itemView.findViewById(R.id.course_row_name);
            itemView.setOnClickListener(this);
        }

        public void setCourse(ICourse course){
            this.course  = course;
            this.courseNameView.setText(course.getName());
        }

        @Override
        public void onClick(View view){
            Context context = view.getContext();
            Intent intent = new Intent(context, UserClass.class);
            intent.putExtra("my_courses", this.course.getName());
            context.startActivity(intent);
        }
    }

}
