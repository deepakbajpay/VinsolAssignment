package com.example.vinsolassignment.di.component;

import android.app.Application;

import com.example.vinsolassignment.VinsolApp;
import com.example.vinsolassignment.di.builder.ActivityBuilder;
import com.example.vinsolassignment.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(VinsolApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}