package com.example.vinsolassignment.di.module;

import com.example.vinsolassignment.data.remote.APIClient;
import com.example.vinsolassignment.ui.utils.rx.AppSchedulerProvider;
import com.example.vinsolassignment.ui.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    APIClient provideApiClient() {
        return new APIClient();
    }
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

}
