package com.urbano.contacts.exception.qrcode;

import com.urbano.contacts.exception.ContactsException;

public class InvalidQRCodeException extends ContactsException {

    public InvalidQRCodeException(int exceptionMessage) {
        super(exceptionMessage);
    }
}
