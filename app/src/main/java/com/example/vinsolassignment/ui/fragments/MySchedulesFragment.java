package com.example.vinsolassignment.ui.fragments;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.example.vinsolassignment.R;
import com.example.vinsolassignment.data.remote.APIClient;
import com.example.vinsolassignment.databinding.FragmentMySchedulesBinding;
import com.example.vinsolassignment.ui.base.BaseFragment;
import com.example.vinsolassignment.ui.schedule.ScheduleAdapter;
import com.example.vinsolassignment.ui.schedule.ScheduleViewModel;
import com.example.vinsolassignment.ui.utils.ViewModelProviderFactory;

import javax.inject.Inject;

public class MySchedulesFragment extends BaseFragment<FragmentMySchedulesBinding, ScheduleViewModel> {

    @Inject
    APIClient apiClient;
    @Inject
    ScheduleAdapter scheduleAdapter;
    @Inject
    ViewModelProviderFactory factory;
    private ScheduleViewModel scheduleViewModel;

    public static MySchedulesFragment newInstance() {

        Bundle args = new Bundle();

        MySchedulesFragment fragment = new MySchedulesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return com.example.vinsolassignment.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_schedules;
    }

    @Override
    public ScheduleViewModel getViewModel() {
        scheduleViewModel = ViewModelProviders.of(this, factory).get(ScheduleViewModel.class);
        return scheduleViewModel;
    }

}
