package com.example.vinsolassignment.ui.schedule;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.vinsolassignment.data.model.ScheduleItem;
import com.example.vinsolassignment.data.remote.APIClient;
import com.example.vinsolassignment.ui.base.BaseViewModel;
import com.example.vinsolassignment.ui.utils.CommonUtils;
import com.example.vinsolassignment.ui.utils.rx.SchedulerProvider;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleViewModel extends BaseViewModel<ScheduleNavigator> {


    public Calendar calendar;
    private final MutableLiveData<List<ScheduleItem>> scheduleListLiveData;
    public String currentDate;
    public ObservableField<String> dateToShow;

    public ScheduleViewModel(APIClient dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        scheduleListLiveData = new MutableLiveData<>();
        calendar = Calendar.getInstance();
        dateToShow = new ObservableField<>();
        fetchSchedules(CommonUtils.getTimestamp("dd/MM/yyyy", calendar.getTime()));
    }

    public MutableLiveData<List<ScheduleItem>> getScheduleListLiveData() {
        return scheduleListLiveData;
    }

    private String getCurrentDate() {
        return CommonUtils.getTimestamp("dd/MM/yyyy", new Date());
    }

    public void fetchSchedules(String date) {
        currentDate = date;
        dateToShow.set(CommonUtils.getTimestamp("dd-MM-yyyy", calendar.getTime()));
        String url = "http://fathomless-shelf-5846.herokuapp.com/api/schedule?date=" + date;
        setIsLoading(true);
        getDataManager().getWebServices()
                .getSchedules(url).enqueue(new Callback<List<ScheduleItem>>() {
            @Override
            public void onResponse(Call<List<ScheduleItem>> call, Response<List<ScheduleItem>> response) {
                System.out.println("ScheduleViewModel.onResponse " + response);
                if (response.body() != null) {
                    List<ScheduleItem> scheduleResponse = response.body();
                    scheduleListLiveData.setValue(scheduleResponse);
                }
                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<List<ScheduleItem>> call, Throwable t) {
                setIsLoading(false);
                System.out.println("ScheduleViewModel.onFailure " + t.getLocalizedMessage());
            }
        });
    }

    public void getNextDaySchedule() {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) + 1);
        fetchSchedules(CommonUtils.getTimestamp("dd/MM/yyyy", calendar.getTime()));
    }

    public void previousDaySchedule() {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - 1);
        fetchSchedules(CommonUtils.getTimestamp("dd/MM/yyyy", calendar.getTime()));
    }

    public void openDatePicker() {
        getNavigator().onDatePickerClick(calendar);
    }

    public void scheduleMeeting() {
        getNavigator().createNewSchedule(calendar);
    }
}
