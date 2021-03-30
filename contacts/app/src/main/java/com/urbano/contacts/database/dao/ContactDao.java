package com.urbano.contacts.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.urbano.contacts.beans.Contact;

import java.util.List;

/**
 * The class the provide to manage the CRUD methods.
 * @author Samuele Urbano
 * @version 1.0
 */
@Dao
public interface ContactDao {

    /**
     * Insert new Contact.
     * @param c {type: Contact} the Contact.
     */
    @Insert
    public long insert(Contact c);

    /**
     * Select the Contacts.
     * @param isDeleted {type: boolean} 'true' if deleted, otherwise 'false'.
     * @return {type: List<Contact>} database's selected Contacts.
     */
    @Query("SELECT * FROM contact WHERE isDeleted = :isDeleted")
    public List<Contact> selectAll(boolean isDeleted);

    @Query("SELECT * FROM contact WHERE id = :id")
    public List<Contact> selectAllById(int id);

    @Query("SELECT * FROM contact WHERE isDeleted = 0 ORDER BY name ASC")
    public List<Contact> selectAllOrderedName();

    @Query("SELECT * FROM contact WHERE isDeleted = 0 ORDER BY surname ASC")
    public List<Contact> selectAllOrderedSurname();

    /**
     * Update the Contact.
     * @param c {type: Contact} the Contact.
     */
    @Update
    public void update(Contact c);

    /**
     * Delete the Contact.
     * @param c {type: Contact} the Contact.
     */
    @Delete
    public void delete(Contact c);
}
