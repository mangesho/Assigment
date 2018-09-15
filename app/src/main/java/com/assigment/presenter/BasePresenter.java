package com.assigment.presenter;


import com.assigment.models.User;

import java.util.List;

public interface BasePresenter {
    void loadUsers();

    void insertAllUsers(List<User> userList);

    List<User> getAllUsers();

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
}
