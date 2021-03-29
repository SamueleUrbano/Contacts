package com.urbano.contacts.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.urbano.contacts.R;
import com.urbano.contacts.adapter.view_holder.ContactsRecyclerViewHolder;
import com.urbano.contacts.beans.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * The class the provide to manage the RecyclerView's Adapter.
 * @author Samuele Urbano
 * @version 1.0
 */
public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerViewHolder> {

    private List<Contact> contacts;

    public ContactsRecyclerAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsRecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsRecyclerViewHolder holder, int position) {
        holder.setContact(this.contacts.get(position));
        holder.getContactCompoundName().setText(this.contacts.get(position).getCompoundName());
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }
}
