package com.urbano.contacts.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.urbano.contacts.R;

/**
 * The class that represents the App Information Activity.
 * @author Samuele Urbano
 * @version 1.0
 */
public class ContactsAppInfoActivity extends AppCompatActivity {

    // The app's version
    private TextView appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info_contacts);

        this.bindWidgets();
        this.appVersion.setText(String.format("%s%s", this.appVersion.getText().toString(), this.getAppVersion()));
    }

    /**
     * Activity widgets binding method.
     */
    private void bindWidgets() {
        this.appVersion = findViewById(R.id.app_version);
    }

    /**
     * Returns the app version in the format "X.X.X.X".
     * @return {type: String} the version.
     */
    private String getAppVersion() {
        try {
            return String.valueOf(this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            return "not visible";
        }
    }
}