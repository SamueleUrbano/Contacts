package com.urbano.contacts.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.urbano.contacts.R;
import com.urbano.contacts.adapter.ContactsBinRecyclerAdapter;
import com.urbano.contacts.beans.Contact;
import com.urbano.contacts.database.DatabaseManager;

import java.util.List;

/**
 * The class that represents the App Bin Activity.
 * @author Samuele Urbano
 * @version 1.0
 */
public class ContactsBinActivity extends AppCompatActivity {

    // The app's toolbar
    private Toolbar toolbar;
    // The app contacts's list
    private static RecyclerView contactsBinRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_contacts);

        this.bindWidgets();
        setSupportActionBar(this.toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.initContactsRecycler(
                DatabaseManager.getInstance(this)
                .getContactDao()
                .selectAll(true)
        );
    }

    /**
     * Reload this after "delete" or "restore" a contact.
     */
    public static void reload() {
        initContactsRecycler(
                DatabaseManager.getInstance(contactsBinRecycler.getContext())
                        .getContactDao()
                        .selectAll(true)
        );
    }

    /**
     * Initialize the contact RecyclerView.
     * @param c {type: List<Contact>} the contacts.
     */
    private static void initContactsRecycler(List<Contact> c) {
        contactsBinRecycler.setAdapter(new ContactsBinRecyclerAdapter(c));
        contactsBinRecycler.setLayoutManager(new LinearLayoutManager(contactsBinRecycler.getContext()));
    }

    /**
     * Activity widgets binding method.
     */
    private void bindWidgets() {
        this.toolbar = findViewById(R.id.toolbar);
        this.contactsBinRecycler = findViewById(R.id.contacts_bin_recycler);
    }
}