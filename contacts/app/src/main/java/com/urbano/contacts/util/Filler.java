package com.urbano.contacts.util;

/**
 * The interface that represents the Filler.
 * @param <T> Type.
 * @author Samuele Urbano
 * @version 1.0
 */
public interface Filler<T> {

    /**
     * The method that fill a form from an Object.
     * @param t {type: T} the Object.
     */
    public void fillWidgetsForm(T t);

    /**
     * The method that fill a new Object from the form.
     * @return {type: T} the new Object.
     */
    public T fillObject();
}
