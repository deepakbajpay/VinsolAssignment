package com.example.vinsolassignment.ui.schedule;

import com.example.vinsolassignment.data.remote.APIClient;
import com.example.vinsolassignment.ui.base.BaseViewModel;
import com.example.vinsolassignment.ui.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel {
    public HomeViewModel(APIClient dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
