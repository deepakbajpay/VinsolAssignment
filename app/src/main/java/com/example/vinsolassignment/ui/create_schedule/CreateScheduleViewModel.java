package com.example.vinsolassignment.ui.create_schedule;

import androidx.databinding.ObservableField;

import com.example.vinsolassignment.data.remote.APIClient;
import com.example.vinsolassignment.ui.base.BaseViewModel;
import com.example.vinsolassignment.ui.utils.rx.SchedulerProvider;

import java.util.List;

public class CreateScheduleViewModel extends BaseViewModel<CreateScheduleNavigator> {


    public ObservableField<String> date;
    public ObservableField<String> startTime;
    public ObservableField<String> endTime;
    public ObservableField<String> description;

    public CreateScheduleViewModel(APIClient dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        startTime = new ObservableField<>();
        endTime = new ObservableField<>();
        description = new ObservableField<>();
        date= new ObservableField<>();
    }

    public void onPickDate() {
        getNavigator().pickDate();
    }

    public void pickStartTime() {
        getNavigator().picStartTime();
    }

    public void pickEndTime() {
        getNavigator().picEndTime();
    }

    public void submit() {
        getNavigator().submit();
    }

    public void goBack(){
        getNavigator().goBack();
    }
}
