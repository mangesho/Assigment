package com.assigment.view;

import com.assigment.models.User;

import java.util.List;

public interface BaseView {

    void showProgressbar();

    void closeProgressbar();

    void showusers(List<User> userList);

    void showError(String message);

    void updateList();
}
