package com.assigment.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user")
public class User {

    @SerializedName("id")
    @Expose
    @NonNull
    @PrimaryKey
    private Integer id;

    @SerializedName("first_name")
    @Expose
    @ColumnInfo(name = "first_name")
    private String firstName;

    @SerializedName("last_name")
    @Expose
    @ColumnInfo(name = "last_name")
    private String lastName;

    @SerializedName("avatar")
    @Expose
    @ColumnInfo(name = "avatar")
    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

}
