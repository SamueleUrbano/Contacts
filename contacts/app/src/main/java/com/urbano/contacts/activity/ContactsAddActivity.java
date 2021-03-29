package com.urbano.contacts.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.urbano.contacts.R;
import com.urbano.contacts.beans.Contact;
import com.urbano.contacts.database.DatabaseManager;
import com.urbano.contacts.exception.InvalidFieldException;
import com.urbano.contacts.exception.RequiredFieldException;
import com.urbano.contacts.util.Filler;
import com.urbano.contacts.util.Utility;

/**
 * The class that represents the Add/Update Activity.
 * @author Samuele Urbano
 * @version 1.0
 */
public class ContactsAddActivity extends AppCompatActivity implements View.OnClickListener, Filler<Contact> {

    // The app's toolbar
    private Toolbar toolbar;

    // Contact's Object fields (EditText)
    private TextInputEditText name;
    private TextInputEditText surname;
    private TextInputEditText telephone;
    private TextInputEditText email;
    private TextInputEditText notes;

    // The cancel button
    private MaterialButton cancelButton;
    // The save button
    private MaterialButton saveButton;

    private Contact contact = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        this.bindWidgets();
        setSupportActionBar(this.toolbar);

        // Getting the intent Extra as Contact
        this.contact = (Contact) getIntent().getSerializableExtra("contact");

        if (this.contact.getId() != 0) {
            this.fillWidgetsForm(this.contact);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.cancelButton.getId()) {
            finish();
        } else if (v.getId() == this.saveButton.getId()) {

            if (this.contact.getId() == 0) {
                // INSERT if is a new Contact
                Contact c = this.fillObject();
                if (c != null) {
                    DatabaseManager.getInstance(v.getContext()).getContactDao().insert(c);
                    finish();
                }
            } else {
                // UPDATE if the Contact already exists
                DatabaseManager.getInstance(v.getContext()).getContactDao().update(this.fillObject());
                finish();
            }
        }
    }


    @Override
    public void fillWidgetsForm(Contact c) {
        this.name.setText(c.getName());
        this.surname.setText(c.getSurname());
        this.telephone.setText(c.getTelephone());
        this.email.setText(c.getEmail());
        this.notes.setText(c.getNotes());
    }

    @Override
    public Contact fillObject() {
        Contact c = new Contact(
                this.contact.getId(),
                this.name.getText().toString(),
                this.surname.getText().toString(),
                this.telephone.getText().toString(),
                this.email.getText().toString(),
                this.notes.getText().toString()
        );

        try {
            return Utility.checkContact(c) ? c : null;
        } catch (RequiredFieldException e) {
            // Making error Snack bar
            Snackbar snackbar = Snackbar.make(findViewById(R.id.save_button), e.getExceptionMessage(), Snackbar.LENGTH_SHORT);
            snackbar.getView()
                    .findViewById(com.google.android.material.R.id.snackbar_text)
                    .setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            snackbar.show();
        } catch (InvalidFieldException e) {
            // Making error Snack bar
            Snackbar snackbar = Snackbar.make(findViewById(R.id.save_button), e.getExceptionMessage(), Snackbar.LENGTH_SHORT);
            snackbar.getView()
                    .findViewById(com.google.android.material.R.id.snackbar_text)
                    .setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            snackbar.show();
        }
        return null;
    }

    /**
     * Activity widgets binding method.
     */
    private void bindWidgets() {
        this.toolbar = findViewById(R.id.toolbar);
        this.name = findViewById(R.id.name);
        this.surname = findViewById(R.id.surname);
        this.telephone = findViewById(R.id.telephone);
        this.email = findViewById(R.id.email);
        this.notes = findViewById(R.id.notes);
        this.cancelButton = findViewById(R.id.cancel_button);
        this.saveButton = findViewById(R.id.save_button);

        // Setting-on button clickListener
        this.cancelButton.setOnClickListener(this);
        this.saveButton.setOnClickListener(this);
    }
}