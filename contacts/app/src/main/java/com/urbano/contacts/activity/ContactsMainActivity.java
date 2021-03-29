package com.urbano.contacts.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.urbano.contacts.R;
import com.urbano.contacts.adapter.ContactsRecyclerAdapter;
import com.urbano.contacts.beans.Contact;
import com.urbano.contacts.database.DatabaseManager;
import com.urbano.contacts.util.Utility;

import java.util.List;

/**
 * The class that represents the main Activity (App start).
 * @author Samuele Urbano
 * @version 1.0
 */
public class ContactsMainActivity extends AppCompatActivity {

    // The external View to get the order types
    private View external;

    // The app's toolbar
    private Toolbar toolbar;
    // The app's filter (SearchView)
    private SearchView contactsSearch;
    // The contacts counter
    private TextView contactsNumber;
    // The app contacts's list
    private RecyclerView contactsRecycler;

    // The contacts's clone (Filtering)
    private List<Contact> contactsClone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contacts);

        this.external = getLayoutInflater().inflate(R.layout.activity_settings_contacts, null);
        this.bindWidgets();
        setSupportActionBar(this.toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Getting the order type from the SharedPreferences
        int idView = Integer.parseInt(
                Utility.readStateFromShared(
                        "order_type",
                        String.valueOf(this.external.findViewById(R.id.order_by_default).getId()),
                        this)
        );

        // Database request by the order type
        if (idView == this.external.findViewById(R.id.order_by_default).getId()) {
            this.initContactsRecycler(DatabaseManager.getInstance(this)
                    .getContactDao()
                    .selectAll(false));
        } else if (idView == this.external.findViewById(R.id.order_by_name).getId()) {
            this.initContactsRecycler(DatabaseManager.getInstance(this)
                    .getContactDao()
                    .selectAllOrderedName());
        } else if (idView == this.external.findViewById(R.id.order_by_surname).getId()) {
            this.initContactsRecycler(DatabaseManager.getInstance(this)
                    .getContactDao()
                    .selectAllOrderedSurname());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contacts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.contact_add) {
            // Calling the add Activity
            Intent intent = new Intent(this, ContactsAddActivity.class);
            intent.putExtra("contact", new Contact());
            startActivity(intent);
        } else if (item.getItemId() == R.id.app_trash) {
            // Calling the trash activity
            startActivity(new Intent(this, ContactsBinActivity.class));
        } else if (item.getItemId() == R.id.app_settings) {
            // Calling the Settings activity
            startActivity(new Intent(this, ContactsSettingsActivity.class));
        } else if (item.getItemId() == R.id.app_info) {
            // Calling the AppInfo activity
            startActivity(new Intent(this, ContactsAppInfoActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize the contact RecyclerView.
     * @param c {type: List<Contact>} the contacts.
     */
    private void initContactsRecycler(List<Contact> c) {
        this.contactsNumber.setText(String.valueOf(c.size()));
        this.contactsRecycler.setAdapter(new ContactsRecyclerAdapter(c));
        this.contactsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Activity widgets binding method.
     */
    private void bindWidgets() {
        this.toolbar = findViewById(R.id.toolbar);
        this.contactsSearch = findViewById(R.id.contacts_search);
        this.contactsNumber = findViewById(R.id.contacts_number);
        this.contactsRecycler = findViewById(R.id.contacts_recycler);
        this.setContactsSearchListener();
    }

    /**
     * Contacts filter implementation.
     */
    private void setContactsSearchListener() {
        this.contactsSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String fromSearch) {
                String searchPattern = fromSearch.toLowerCase().trim();
                contactsClone = DatabaseManager.getInstance(ContactsMainActivity.this.getBaseContext())
                        .getContactDao()
                        .selectAll(false);

                if (!fromSearch.isEmpty()) {
                    for (int i = 0; i < contactsClone.size(); i++) {
                        if (!contactsClone.get(i).getCompoundName().toLowerCase().contains(searchPattern)) {
                            contactsClone.remove(i);
                            i--;
                        }
                    }
                    initContactsRecycler(contactsClone);
                } else {
                    initContactsRecycler(
                            DatabaseManager.getInstance(ContactsMainActivity.this.getBaseContext())
                                    .getContactDao()
                                    .selectAll(false));
                }
                return false;
            }
        });
    }
}