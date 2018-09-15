package com.assigment.presenter;


import com.assigment.data.UserDao;
import com.assigment.models.User;
import com.assigment.models.UserData;
import com.assigment.view.BaseView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class BasePresenterImpl implements BasePresenter {

    public Retrofit retrofit;
    public BaseView baseView;
    public UserDao userDao;

    @Inject
    public BasePresenterImpl(Retrofit retrofit, BaseView baseView,UserDao userDao) {
        this.retrofit = retrofit;
        this.baseView = baseView;
        this.userDao = userDao;
    }

    @Override
    public void loadUsers() {
        baseView.showProgressbar();
        retrofit.create(UserService.class).getUserData().enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                List<User> userList = response.body().getData();
                insertAllUsers(userList);
                baseView.showusers(userList);
                baseView.closeProgressbar();
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                baseView.showError(t.getMessage().toString());
                baseView.closeProgressbar();
            }
        });
    }

    @Override
    public void insertAllUsers(List<User> userList) {
        userDao.insertAllUsers(userList);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void addUser(User user) {
        user.setId(userDao.getCount() + 1);
        userDao.insertUser(user);
        baseView.updateList();
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
        baseView.updateList();
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
        baseView.updateList();
    }

    public interface UserService {
        @GET("api/users?page=1")
        Call<UserData> getUserData();
    }
}
