package com.benlau.bofteam1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benlau.bofteam1.db.Course;

import org.w3c.dom.Text;

import java.util.List;
import java.util.function.Consumer;

public class CoursesViewAdapter extends RecyclerView.Adapter<CoursesViewAdapter.ViewHolder> {
    private final List<Course> courses;
    private final Consumer<Course> onCourseRemoved;

    public CoursesViewAdapter(List<Course> courses, Consumer<Course> onCourseRemoved){
        super();
        this.courses = courses;
        this.onCourseRemoved = onCourseRemoved;
    }

    @NonNull
    @Override
    public CoursesViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.course_row, parent, false);
        return new ViewHolder(view, this::removeCourse, onCourseRemoved);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewAdapter.ViewHolder holder, int position){
        holder.setCourse(courses.get(position));
    }

    @Override
    public int getItemCount(){
        return this.courses.size();
    }

    public void addCourse(Course course){
        this.courses.add(course);
        this.notifyItemInserted(this.courses.size() - 1);
    }

    public void removeCourse(int position){
        this.courses.remove(position);
        this.notifyItemRemoved(position);
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {
        private final TextView courseNameView;
        private Course course;

        ViewHolder(View itemView, Consumer<Integer> removeCourse, Consumer<Course> onCourseRemoved){
            super(itemView);
            this.courseNameView = itemView.findViewById(R.id.course_row_name);

            Button removeButton = itemView.findViewById(R.id.remove_course_button);
            removeButton.setOnClickListener((view) -> {
                removeCourse.accept(this.getAdapterPosition());
                onCourseRemoved.accept(course);
            });
/*
            TextView yearText = itemView.findViewById(R.id.year);
            TextView courseText = itemView.findViewById(R.id.course);
            TextView courseIdText = itemView.findViewById(R.id.courseID);
            Spinner quarterSpinner = itemView.findViewById(R.id.quarter);

            Button editButton = itemView.findViewById(R.id.edit_course);
            editButton.setOnClickListener((view) -> {
                removeCourse.accept(this.getAdapterPosition());
                onCourseRemoved.accept(course);

                yearText.setText(course.year);
                courseText.setText(course.courseName);
                courseIdText.setText(course.id);

            });
*/
        }

        public void setCourse(Course course){
            this.course  = course;
            this.courseNameView.setText(course.getCourse());
        }
    }

}
