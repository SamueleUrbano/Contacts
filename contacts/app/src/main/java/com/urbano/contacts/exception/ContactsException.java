package com.urbano.contacts.exception;

import androidx.annotation.StringRes;

public class ContactsException extends Exception {

    @StringRes private int exceptionMessage;

    public ContactsException(int exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public int getExceptionMessage() {
        return this.exceptionMessage;
    }
}
