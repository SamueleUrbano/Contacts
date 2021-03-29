package com.urbano.contacts.exception;

public class RequiredFieldException extends ContactsException {

    public RequiredFieldException(int exceptionMessage) {
        super(exceptionMessage);
    }
}
