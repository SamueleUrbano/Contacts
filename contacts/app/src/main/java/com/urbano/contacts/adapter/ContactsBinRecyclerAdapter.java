package com.urbano.contacts.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.urbano.contacts.R;
import com.urbano.contacts.adapter.view_holder.ContactsBinRecyclerViewHolder;
import com.urbano.contacts.adapter.view_holder.ContactsRecyclerViewHolder;
import com.urbano.contacts.beans.Contact;

import java.util.List;

public class ContactsBinRecyclerAdapter extends RecyclerView.Adapter<ContactsBinRecyclerViewHolder> {

    private List<Contact> contacts;

    public ContactsBinRecyclerAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactsBinRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsBinRecyclerViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.card_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsBinRecyclerViewHolder holder, int position) {
        holder.setContact(this.contacts.get(position));
        holder.getContactCompoundName().setText(this.contacts.get(position).getCompoundName());
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }
}
