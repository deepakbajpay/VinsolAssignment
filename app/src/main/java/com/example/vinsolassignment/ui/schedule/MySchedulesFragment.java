package com.example.vinsolassignment.ui.schedule;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vinsolassignment.R;
import com.example.vinsolassignment.databinding.FragmentMySchedulesBinding;
import com.example.vinsolassignment.ui.base.BaseFragment;
import com.example.vinsolassignment.ui.create_schedule.CreateScheduleFragment;
import com.example.vinsolassignment.ui.utils.CommonUtils;
import com.example.vinsolassignment.ui.utils.ViewModelProviderFactory;

import java.util.Calendar;

import javax.inject.Inject;

public class MySchedulesFragment extends BaseFragment<FragmentMySchedulesBinding, ScheduleViewModel> implements ScheduleNavigator, DatePickerDialog.OnDateSetListener {

    @Inject
    public LinearLayoutManager mLayoutManager;
    @Inject
    ScheduleAdapter scheduleAdapter;
    @Inject
    ViewModelProviderFactory factory;
    FragmentMySchedulesBinding mySchedulesBinding;
    private ScheduleViewModel scheduleViewModel;

    public static MySchedulesFragment newInstance() {
        Bundle args = new Bundle();
        MySchedulesFragment fragment = new MySchedulesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduleViewModel.setNavigator(this);
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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mySchedulesBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mySchedulesBinding.scheduleRecyclerView.setLayoutManager(mLayoutManager);
        mySchedulesBinding.scheduleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mySchedulesBinding.scheduleRecyclerView.setAdapter(scheduleAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);

        mySchedulesBinding.scheduleRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void createNewSchedule(Calendar currentDate) {
        openFragment(CreateScheduleFragment.newInstance(CommonUtils.getTimestamp("dd/MM/yyyy", currentDate.getTime())), MySchedulesFragment.class.getName());
    }

    @Override
    public void onDatePickerClick(Calendar currentDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        System.out.println("datePicker = [" + datePicker + "], i = [" + i + "], i1 = [" + i1 + "], i2 = [" + i2 + "]");
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i1, i2);
        scheduleViewModel.calendar = calendar;
        scheduleViewModel.fetchSchedules(CommonUtils.getTimestamp("dd/MM/yyyy", calendar.getTime()));
    }
}
