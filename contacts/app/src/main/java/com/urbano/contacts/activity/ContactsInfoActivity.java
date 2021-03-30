package com.urbano.contacts.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.urbano.contacts.R;
import com.urbano.contacts.beans.Contact;
import com.urbano.contacts.database.DatabaseManager;
import com.urbano.contacts.dialog.QRViewDialog;
import com.urbano.contacts.util.Filler;
import com.urbano.contacts.util.Utility;

/**
 * The class that represents the Contact Information Activity.
 * @author Urbano Samuele
 * @version 1.0
 */
public class ContactsInfoActivity extends AppCompatActivity implements View.OnClickListener, Filler<Contact> {

    // The buttons
    private MaterialButton callButton;
    private MaterialButton emailButton;
    private MaterialButton editButton;
    private MaterialButton deleteButton;
    private MaterialButton generateQRCodeButton;

    // The text views
    private TextView infoCompoundName;
    private TextView infoTelephone;
    private TextView infoEmail;
    private TextView infoNotes;

    private Contact contact = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_contacts);

        this.bindWidgets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.contact = DatabaseManager.getInstance(this)
                .getContactDao()
                .selectAllById(((Contact)getIntent().getSerializableExtra("contact")).getId())
                .get(0);
        this.fillWidgetsForm(contact);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.callButton.getId()) {
            // On call_button pressed
            Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + this.infoTelephone.getText().toString()));

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
                return;
            }
            startActivity(call);
        } else if (v.getId() == this.emailButton.getId()) {
            // On email_button pressed
            // Check if an email is present
            if (this.contact.getEmail() != null && !this.contact.getEmail().isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, this.contact.getEmail());
                startActivity(intent);
            } else {
                // Making a Snack bar
                Snackbar snackbar = Snackbar.make(findViewById(R.id.email_button), R.string.email_send, Snackbar.LENGTH_SHORT);
                snackbar.getView()
                        .findViewById(com.google.android.material.R.id.snackbar_text)
                        .setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                snackbar.show();
            }
        } else if (v.getId() == this.editButton.getId()) {
            // On edit_button pressed
            Intent intent = new Intent(this, ContactsAddActivity.class);
            intent.putExtra("contact", this.contact);
            startActivity(intent);
        } else if (v.getId() == this.deleteButton.getId()) {
            // On delete_button pressed
            // Check if keep_deleted is true (keep contacts after deleting)
            new AlertDialog.Builder(v.getContext())
                    .setTitle(R.string.delete_alert_title)
                    .setMessage(R.string.delete_alert_message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (Boolean.parseBoolean(Utility.readStateFromShared("keep_deleted", "true", getApplicationContext()))) {
                                contact.setDeleted(true);
                                DatabaseManager.getInstance(getApplicationContext()).getContactDao().update(contact);
                            } else {
                                DatabaseManager.getInstance(getApplicationContext()).getContactDao().delete(contact);
                            }
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        } else if (v.getId() == this.generateQRCodeButton.getId()) {
            new QRViewDialog(this, Utility.generateQrCode(this.contact)).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        super.onRequestPermissionsResult(requestCode, permissions, results);

        for (int result : results) {
            if (result == PackageManager.PERMISSION_GRANTED) {
                Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + this.infoTelephone.getText().toString()));
                call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(call);
            } else {
                // Making a Snack bar
                Snackbar snackbar = Snackbar.make(findViewById(R.id.call_button), R.string.permission_denied, Snackbar.LENGTH_SHORT);
                snackbar.getView()
                        .findViewById(com.google.android.material.R.id.snackbar_text)
                        .setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                snackbar.show();
            }
        }
    }

    @Override
    public void fillWidgetsForm(Contact c) {
        this.infoCompoundName.setText(c.getCompoundName());
        this.infoTelephone.setText(c.getTelephone());
        this.infoEmail.setText(c.getEmail());
        this.infoNotes.setText(c.getNotes());
    }

    @Override
    public Contact fillObject() {
        return null;
    }

    /**
     * Activity widgets binding method.
     */
    private void bindWidgets() {
        this.callButton = findViewById(R.id.call_button);
        this.emailButton = findViewById(R.id.email_button);
        this.editButton = findViewById(R.id.edit_button);
        this.deleteButton = findViewById(R.id.delete_button);
        this.generateQRCodeButton = findViewById(R.id.generate_qr_code_button);
        this.infoCompoundName = findViewById(R.id.info_compound_name);
        this.infoTelephone = findViewById(R.id.info_telephone);
        this.infoEmail = findViewById(R.id.info_email);
        this.infoNotes = findViewById(R.id.info_notes);

        // Setting-on button clickListener
        this.callButton.setOnClickListener(this);
        this.emailButton.setOnClickListener(this);
        this.editButton.setOnClickListener(this);
        this.deleteButton.setOnClickListener(this);
        this.generateQRCodeButton.setOnClickListener(this);
    }
}