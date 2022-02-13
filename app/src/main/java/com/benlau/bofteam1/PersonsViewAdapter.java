package com.benlau.bofteam1;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benlau.bofteam1.db.Person;

import java.util.List;

import com.benlau.bofteam1.db.Person;

//need to populate persons_row.xml
public class PersonsViewAdapter extends RecyclerView.Adapter<PersonsViewAdapter.ViewHolder> {
    private final List<Person> persons;

    public PersonsViewAdapter(List<Person> persons) {
        super();
        this.persons = persons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.persons_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPerson(persons.get(position));
    }

    @Override
    public int getItemCount() {
        return this.persons.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView personNameView;
        private Person person;

        ViewHolder(View itemView) {
            super(itemView);

            this.personNameView = itemView.findViewById(R.id.person_row_name);
            itemView.setOnClickListener(this);
        }

        public void setPerson(Person person) {
            this.person = person;
            this.personNameView.setText(person.getPersonName());
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            //insert activity responsible or personsDetails in the intent, then uncomment the other code.
            //Intent intent = new Intent(context, PersonDetailActivity.class);
            //intent.putExtra("person_name", this.person.getPersonId());
            //context.startActivity(intent);
        }
    }
}
