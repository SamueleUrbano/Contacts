package com.urbano.contacts.adapter.view_holder;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.urbano.contacts.R;
import com.urbano.contacts.activity.ContactsInfoActivity;
import com.urbano.contacts.beans.Contact;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The class the provide to manage the RecyclerView's ViewHolder.
 * @author Samuele Urbano
 * @version 1.0
 */
public class ContactsRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView contactCompoundName;
    private Contact contact;

    public ContactsRecyclerViewHolder(@NonNull View v) {
        super(v);
        this.contactCompoundName = v.findViewById(R.id.card_contact_compound_name);
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ContactsInfoActivity.class);
        intent.putExtra("contact", this.contact);
        v.getContext().startActivity(intent);
    }

    public TextView getContactCompoundName() {
        return contactCompoundName;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
