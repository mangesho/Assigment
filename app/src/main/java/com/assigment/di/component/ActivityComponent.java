package com.assigment.di.component;

import com.assigment.di.module.ActivityModule;
import com.assigment.utility.CustomScope;
import com.assigment.view.MainActivity;

import dagger.Component;

@CustomScope
@Component(dependencies = ApiComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
}
