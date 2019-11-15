package com.example.vinsolassignment.ui.schedule;

import androidx.lifecycle.MutableLiveData;

import com.example.vinsolassignment.data.model.ScheduleItem;
import com.example.vinsolassignment.data.remote.APIClient;
import com.example.vinsolassignment.ui.base.BaseViewModel;
import com.example.vinsolassignment.ui.utils.rx.SchedulerProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleViewModel extends BaseViewModel {


    public MutableLiveData<List<ScheduleItem>> getScheduleListLiveData() {
        return scheduleListLiveData;
    }

    private final MutableLiveData<List<ScheduleItem>> scheduleListLiveData;
    public String currentDate;

    public ScheduleViewModel(APIClient dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        scheduleListLiveData = new MutableLiveData<>();
        currentDate = getCurrentDate();
        fetchSchedules(currentDate);
    }

    private String getCurrentDate() {
        return null;
    }

    private void fetchSchedules(String date) {
        String url = "http://fathomless-shelf-5846.herokuapp.com/api/schedule?date=" + date;
        setIsLoading(true);
        getDataManager().getWebServices()
                .getSchedules(url).enqueue(new Callback<List<ScheduleItem>>() {
            @Override
            public void onResponse(Call<List<ScheduleItem>> call, Response<List<ScheduleItem>> response) {
                System.out.println("ScheduleViewModel.onResponse "+response);
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
                /*.subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe((scheduleResponse) -> {
                    if (scheduleResponse != null && scheduleResponse.size() > 0) {
                        scheduleListLiveData.setValue(scheduleResponse);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                }));*/
    }

    public void getNextDaySchedule() {

        fetchSchedules("24/7/2019");
    }

    public void previousDaySchedule() {
        fetchSchedules("22/7/2019");
    }

    public void openDatePicker() {

        fetchSchedules("24/7/2019");
    }
}
