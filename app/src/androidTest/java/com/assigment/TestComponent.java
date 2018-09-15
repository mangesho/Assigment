package com.assigment;

import android.app.Application;

import com.assigment.data.UserDao;
import com.assigment.di.module.AppModule;
import com.assigment.di.module.DataModule;
import com.assigment.utility.CustomScope;

import javax.inject.Singleton;

import dagger.Component;

@CustomScope
@Singleton
@Component(modules = {DataModule.class,AppModule.class})
public interface TestComponent{
    void inject(UserAddInsertUpdateDeleteTest test);

    Application application();

    UserDao userDao();
}
