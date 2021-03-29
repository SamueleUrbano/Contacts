package com.urbano.contacts.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;

import com.urbano.contacts.beans.Contact;
import com.urbano.contacts.database.dao.ContactDao;

/**
 * The class the provide to manage the App's database.
 * @author Samuele Urbano
 * @version 1.0
 */
@Database(entities = {Contact.class}, version = 1)
public abstract class DatabaseManager extends RoomDatabase {

    private static final String DB_NAME = "contacts";
    private static DatabaseManager instance;

    public static DatabaseManager getInstance(Context c) {
        if (instance == null) {
            instance = Room.databaseBuilder(c, DatabaseManager.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract ContactDao getContactDao();
}
