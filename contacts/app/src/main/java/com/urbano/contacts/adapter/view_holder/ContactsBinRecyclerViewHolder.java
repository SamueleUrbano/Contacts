package com.urbano.contacts.adapter.view_holder;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.urbano.contacts.R;
import com.urbano.contacts.activity.ContactsBinActivity;
import com.urbano.contacts.beans.Contact;
import com.urbano.contacts.database.DatabaseManager;

public class ContactsBinRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private TextView contactCompoundName;
    private Contact contact;

    public ContactsBinRecyclerViewHolder(@NonNull View v) {
        super(v);
        this.contactCompoundName = v.findViewById(R.id.card_contact_compound_name);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new AlertDialog.Builder(v.getContext())
                .setTitle(R.string.delete_alert_title)
                .setMessage(R.string.delete_alert_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseManager.getInstance(v.getContext())
                                .getContactDao()
                                .delete(contact);

                        ContactsBinActivity.reload();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public boolean onLongClick(View v) {
        new AlertDialog.Builder(v.getContext())
                .setTitle(R.string.restore_alert_title)
                .setMessage(R.string.restore_alert_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        contact.setDeleted(false);
                        DatabaseManager.getInstance(v.getContext())
                                .getContactDao()
                                .update(contact);

                        ContactsBinActivity.reload();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
        return false;
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
