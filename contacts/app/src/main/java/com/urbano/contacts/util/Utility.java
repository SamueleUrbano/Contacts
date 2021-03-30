package com.urbano.contacts.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.urbano.contacts.R;
import com.urbano.contacts.beans.Contact;
import com.urbano.contacts.exception.InvalidFieldException;
import com.urbano.contacts.exception.RequiredFieldException;
import com.urbano.contacts.exception.qrcode.InvalidQRCodeException;

import java.io.IOException;

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

    public static Contact parseFromQrCode(String qrCodeValue) throws InvalidQRCodeException {
        Contact contact = null;
        try {
            contact = new ObjectMapper().readValue(qrCodeValue, Contact.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidQRCodeException(R.string.invalid_qr_code);
        }
        return contact;
    }

    public static Bitmap generateQrCode(Contact c) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(new ObjectMapper().writeValueAsString(c), BarcodeFormat.QR_CODE, 400, 400);
            return new BarcodeEncoder().createBitmap(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}