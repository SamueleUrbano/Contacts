package com.urbano.contacts.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.urbano.contacts.R;
import com.urbano.contacts.beans.Contact;
import com.urbano.contacts.exception.InvalidFieldException;
import com.urbano.contacts.exception.RequiredFieldException;

public class Utility {

    private static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static void writeStateOnShared(String key, String value, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("contacts_settings", Context.MODE_PRIVATE).edit();

        editor.putString(key, value);
        editor.apply();
    }

    public static String readStateFromShared(String key, String defaultValue, Context context) {
        return context.getSharedPreferences("contacts_settings", Context.MODE_PRIVATE).getString(key, defaultValue);
    }

    public static boolean checkContact(Contact contact) throws RequiredFieldException, InvalidFieldException {
        if ((contact.getName() != null && !contact.getName().isEmpty()) && (contact.getTelephone() != null && !contact.getTelephone().isEmpty())) {
            if (contact.getEmail().isEmpty() || contact.getEmail().matches(EMAIL_PATTERN)) {
                return true;
            } else {
                throw new InvalidFieldException(R.string.invalid_field);
            }
        } else {
            throw new RequiredFieldException(R.string.required_field);
        }
    }
}