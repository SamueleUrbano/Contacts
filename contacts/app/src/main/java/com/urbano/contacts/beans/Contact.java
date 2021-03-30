package com.urbano.contacts.beans;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(indices = {@Index(value = {"telephone"}, unique = true)})
public class Contact implements Serializable {

    @PrimaryKey (autoGenerate = true)
    int id;

    @NonNull
    private String name;
    private String surname;

    @NonNull
    private String telephone;
    private String email;
    private String notes;
    private boolean isDeleted = false;

    public Contact() {

    }

    public Contact(@NonNull String name, String surname, @NonNull String telephone, String email, String notes) {
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.email = email;
        this.notes = notes;
    }

    public Contact(int id, @NonNull String name, String surname, @NonNull String telephone, String email, String notes) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.email = email;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", notes='" + notes + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @NonNull
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(@NonNull String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getCompoundName() {
        return name + " " + surname;
    }

    public void setCompoundName(String compoundName) {

    }
}