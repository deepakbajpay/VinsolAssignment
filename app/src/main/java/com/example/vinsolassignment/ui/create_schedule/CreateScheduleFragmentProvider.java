package com.example.vinsolassignment.ui.create_schedule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CreateScheduleFragmentProvider {

    @ContributesAndroidInjector
    abstract CreateScheduleFragment provideAboutFragmentFactory();
}
