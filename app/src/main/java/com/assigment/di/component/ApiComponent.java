package com.assigment.di.component;

import com.assigment.data.UserDao;
import com.assigment.data.UserDatabase;
import com.assigment.di.module.ApiModule;
import com.assigment.di.module.AppModule;
import com.assigment.di.module.DataModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class, DataModule.class})
public interface ApiComponent {
    Retrofit retrofit();

    UserDatabase userDatabase();

    UserDao userDao();
}
