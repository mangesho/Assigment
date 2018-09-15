package com.assigment.di.module;

import com.assigment.view.BaseView;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final BaseView mView;


    public ActivityModule(BaseView mView) {
        this.mView = mView;
    }

    @Provides
    BaseView providesBaseView() {
        return mView;
    }
}
