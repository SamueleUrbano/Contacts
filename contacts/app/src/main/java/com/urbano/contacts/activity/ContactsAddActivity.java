package com.urbano.contacts.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
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
import com.urbano.contacts.exception.qrcode.InvalidQRCodeException;
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
    // The import from QRCode button
    private MaterialButton qrCodeButton;

    private Contact contact = null;

    public static String forQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        this.bindWidgets();
        setSupportActionBar(this.toolbar);

        // Getting the intent Extra as Contact
        this.contact = (Contact) getIntent().getSerializableExtra("contact");

        if (this.contact.getId() != 0) {
            this.qrCodeButton.setVisibility(View.INVISIBLE);
            this.fillWidgetsForm(this.contact);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.fillWidgetsForm(this.contact);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            this.contact = Utility.parseFromQrCode(forQRCode);
            this.contact.setId(0);
        } catch (InvalidQRCodeException e) {
            // Making error Snack bar
            Snackbar snackbar = Snackbar.make(findViewById(R.id.qr_code_button), e.getExceptionMessage(), Snackbar.LENGTH_LONG);
            snackbar.getView()
                    .findViewById(com.google.android.material.R.id.snackbar_text)
                    .setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            snackbar.show();
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
                Log.d("c", c.toString());
                if (c != null) {
                    // long b = DatabaseManager.getInstance(v.getContext()).getContactDao().insert(c);
                    try {
                        DatabaseManager.getInstance(v.getContext()).getContactDao().insert(c);
                        finish();
                    } catch (SQLiteConstraintException e) {
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.save_button), R.string.contact_already_exists, Snackbar.LENGTH_SHORT);
                        snackbar.getView()
                                .findViewById(com.google.android.material.R.id.snackbar_text)
                                .setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        snackbar.show();
                    }
                }
            } else {
                // UPDATE if the Contact already exists
                DatabaseManager.getInstance(v.getContext()).getContactDao().update(this.fillObject());
                finish();
            }
        } else if (v.getId() == this.qrCodeButton.getId()) {
            // On qr_code_button pressed
            Intent qrCodeScan = new Intent(this, QRCodeScanActivity.class);

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},2);
                return;
            }
            startActivity(qrCodeScan);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        super.onRequestPermissionsResult(requestCode, permissions, results);

        for (int result : results) {
            if (result == PackageManager.PERMISSION_GRANTED) {
                Intent qrCodeScan = new Intent(this, QRCodeScanActivity.class);
                // qrCodeScan.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(qrCodeScan);
            } else {
                // Making a Snack bar
                Snackbar snackbar = Snackbar.make(findViewById(R.id.qr_code_button), R.string.permission_denied, Snackbar.LENGTH_SHORT);
                snackbar.getView()
                        .findViewById(com.google.android.material.R.id.snackbar_text)
                        .setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                snackbar.show();
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
        this.qrCodeButton = findViewById(R.id.qr_code_button);

        // Setting-on button clickListener
        this.cancelButton.setOnClickListener(this);
        this.saveButton.setOnClickListener(this);
        this.qrCodeButton.setOnClickListener(this);
    }
}