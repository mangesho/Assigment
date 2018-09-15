package com.assigment;

import android.app.Application;

import com.assigment.di.component.ApiComponent;
import com.assigment.di.component.DaggerApiComponent;
import com.assigment.di.module.ApiModule;
import com.assigment.di.module.AppModule;
import com.assigment.di.module.DataModule;

public class App extends Application {

    private ApiComponent mApiComponent;

    private static App appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        mApiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule("https://reqres.in/"))
                .dataModule(new DataModule())
                .build();
    }

    public ApiComponent getNetComponent() {
        return mApiComponent;
    }

    public static App getAppInstance() {
        return appInstance;
    }
}
