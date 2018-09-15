package com.assigment.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.assigment.data.UserDao;
import com.assigment.data.UserDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {
    @Singleton
    @Provides
    public UserDatabase provideMyDatabase(Application context){
        return Room.databaseBuilder(context, UserDatabase.class, "user-db").allowMainThreadQueries().build();
    }

    @Singleton @Provides
    public UserDao provideUserDao(UserDatabase myDatabase){
        return myDatabase.userDao();
    }
}
