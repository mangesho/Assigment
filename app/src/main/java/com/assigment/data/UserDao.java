package com.assigment.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.assigment.models.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT COUNT(*) FROM user")
    int getCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUsers(List<User> userList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User... users);

    @Update
    void updateUser(User... users);

    @Delete
    void deleteUser(User... users);

    @Query("DELETE FROM user")
    void deleteAllUsers();

    @Query("DELETE FROM user WHERE id = :userId")
    void deleteByUserId(Integer userId);
}