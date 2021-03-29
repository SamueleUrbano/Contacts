package com.urbano.contacts.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.urbano.contacts.R;
import com.urbano.contacts.util.Utility;

/**
 * The class that represents App Settings Activity.
 * @author Samuele Urbano
 * @version 1.0
 */
public class ContactsSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    // The app's toolbar
    private Toolbar toolbar;
    // Radio buttons
    private MaterialRadioButton orderByDefault;
    private MaterialRadioButton orderByName;
    private MaterialRadioButton orderBySurname;
    // The switch
    private SwitchMaterial keepDeleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_contacts);

        this.bindWidgets();
        setSupportActionBar(this.toolbar);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.orderByDefault.getId()) {
            this.orderByDefault.setChecked(true);
            this.changeOrderType(this.orderByDefault.getId());
        } else if (v.getId() == this.orderByName.getId()) {
            this.orderByName.setChecked(true);
            this.changeOrderType(this.orderByName.getId());
        } else if (v.getId() == this.orderBySurname.getId()) {
            this.orderBySurname.setChecked(true);
            this.changeOrderType(this.orderBySurname.getId());
        }
    }

    /**
     * Activity widgets binding method.
     */
    private void bindWidgets() {
        this.toolbar = findViewById(R.id.toolbar);
        this.orderByDefault = findViewById(R.id.order_by_default);
        this.orderByName = findViewById(R.id.order_by_name);
        this.orderBySurname = findViewById(R.id.order_by_surname);
        this.keepDeleted = findViewById(R.id.keep_deleted);

        // Importing the saved settings
        this.importSettings();

        this.orderByDefault.setOnClickListener(this);
        this.orderByName.setOnClickListener(this);
        this.orderBySurname.setOnClickListener(this);

        this.keepDeleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utility.writeStateOnShared("keep_deleted",
                        String.valueOf(isChecked),
                        keepDeleted.getContext());
            }
        });
    }

    /**
     * Imports the settings from the App's SharedPreferences.
     */
    private void importSettings() {

        int idView = Integer.parseInt(
                Utility.readStateFromShared(
                    "order_type",
                    String.valueOf(this.orderByDefault.getId()),
                    this)
            );

        if (this.orderByDefault.getId() == idView) {
            this.orderByDefault.setChecked(true);
        } else if (this.orderByName.getId() == idView) {
            this.orderByName.setChecked(true);
        } else if (this.orderBySurname.getId() == idView) {
            this.orderBySurname.setChecked(true);
        }

        this.keepDeleted.setChecked(
                Boolean.parseBoolean(
                        Utility.readStateFromShared(
                            "keep_deleted",
                            "true",
                            this.keepDeleted.getContext())
                )
        );
    }

    /**
     * Set the new contacts order type in the SharedPreferences.
     * @param idView {type: int} the new order type.
     */
    private void changeOrderType(int idView) {
        Utility.writeStateOnShared(
                "order_type",
                String.valueOf(idView),
                this
        );
    }
}