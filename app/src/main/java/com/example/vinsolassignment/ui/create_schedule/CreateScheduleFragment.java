package com.example.vinsolassignment.ui.create_schedule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.vinsolassignment.BR;
import com.example.vinsolassignment.R;
import com.example.vinsolassignment.data.model.ScheduleItem;
import com.example.vinsolassignment.data.remote.APIClient;
import com.example.vinsolassignment.databinding.FragmentCreateScheduleBinding;
import com.example.vinsolassignment.ui.base.BaseFragment;
import com.example.vinsolassignment.ui.utils.CommonUtils;
import com.example.vinsolassignment.ui.utils.ViewModelProviderFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class CreateScheduleFragment extends BaseFragment<FragmentCreateScheduleBinding, CreateScheduleViewModel> implements CreateScheduleNavigator, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final String CURRENT_DATE = "current_date";

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    APIClient apiClient;

    Date pickedDate;
    Date pickedStartTime;
    Date pickedEndTime;
    String format = "#0.00";

    CreateScheduleViewModel createScheduleViewModel;
    private CreateScheduleNavigator mNavigator;
    private String currentDate;
    private ArrayList<ScheduleItem> scheduleItems;
    private boolean isStartDate;
    private String startTimeStr = null, endTimeStr = null;

    public static CreateScheduleFragment newInstance(String currentDate) {

        Bundle args = new Bundle();
        args.putString(CURRENT_DATE, currentDate);
        CreateScheduleFragment fragment = new CreateScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_create_schedule;
    }

    @Override
    public CreateScheduleViewModel getViewModel() {
        createScheduleViewModel = ViewModelProviders.of(this, factory).get(CreateScheduleViewModel.class);
        return createScheduleViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createScheduleViewModel.setNavigator(this);

        if (getArguments() != null) {
            currentDate = getArguments().getString(CURRENT_DATE);

            createScheduleViewModel.date.set(currentDate);
            pickedDate = CommonUtils.getDateFromString(currentDate, "dd/MM/yyyy");
            fetchSchedules(currentDate);
        }
        scheduleItems = new ArrayList<>();
    }

    @Override
    public void submit() {
        if (startTimeStr == null || endTimeStr == null) {
            Toast.makeText(getBaseActivity(), "Please select start and end times", Toast.LENGTH_SHORT).show();
            return;
        }
        pickedStartTime = CommonUtils.getDateFromString(startTimeStr, "HH:mm");
        pickedEndTime = CommonUtils.getDateFromString(endTimeStr, "HH:mm");
        if (pickedEndTime.before(pickedStartTime)){
            Toast.makeText(getBaseActivity(), "End time should be after start time", Toast.LENGTH_SHORT).show();
        }
        boolean slotAvailable = true;
        if (scheduleItems != null && scheduleItems.size() > 0) {
            for (ScheduleItem scheduleItem : scheduleItems) {
                Date startTime = CommonUtils.getDateFromString(scheduleItem.getStartTime(), "HH:mm");
                Date endTime = CommonUtils.getDateFromString(scheduleItem.getEndTime(), "HH:mm");
                if (startTime != null && endTime != null) {
                    if (!((pickedStartTime.before(startTime) && pickedEndTime.before(startTime)) || (pickedEndTime.after(endTime) && pickedStartTime.after(endTime))))
                    {
                        slotAvailable = false;
                        Toast.makeText(getBaseActivity(), "Choosen time slot not available. Please select another slot", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }

        if (slotAvailable) {
            Toast.makeText(getBaseActivity(), "Meeting scheduled", Toast.LENGTH_SHORT).show();
            getBaseActivity().onBackPressed();
            //scheduleMeeting
        }
    }

    @Override
    public void pickDate() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(pickedDate);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        System.out.println("datePicker = [" + datePicker + "], i = [" + i + "], i1 = [" + i1 + "], i2 = [" + i2 + "]");
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i1, i2);
        pickedDate = calendar.getTime();
        currentDate = CommonUtils.getTimestamp("dd/MM/yyyy", pickedDate);
        createScheduleViewModel.date.set(currentDate);
        fetchSchedules(currentDate);
    }

    @Override
    public void picStartTime() {
        isStartDate = true;
        TimePickerDialog timePickerDialog = new TimePickerDialog(getBaseActivity(), this, 12, 0, true);
        timePickerDialog.show();
    }

    @Override
    public void picEndTime() {
        isStartDate = false;
        TimePickerDialog timePickerDialog = new TimePickerDialog(getBaseActivity(), this, 12, 0, true);
        timePickerDialog.show();
    }

    @Override
    public void goBack() {
        getBaseActivity().onBackPressed();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        NumberFormat formatter = new DecimalFormat("00");
        System.out.println("CreateScheduleFragment.onTimeSet "+formatter.format(i));
        formatter.format(i);
        formatter.format(i1);
        if (isStartDate) {
            startTimeStr = formatter.format(i) + ":" +formatter.format(i1);
            createScheduleViewModel.startTime.set(startTimeStr);
        } else {
            endTimeStr = formatter.format(i) + ":" + formatter.format(i1);
            createScheduleViewModel.endTime.set(endTimeStr);
        }
    }

    public void fetchSchedules(String date) {
        currentDate = date;
        String url = "http://fathomless-shelf-5846.herokuapp.com/api/schedule?date=" + date;
        createScheduleViewModel.setIsLoading(true);
        apiClient.getWebServices()
                .getSchedules(url).enqueue(new retrofit2.Callback<List<ScheduleItem>>() {
            @Override
            public void onResponse(Call<List<ScheduleItem>> call, Response<List<ScheduleItem>> response) {
                System.out.println("ScheduleViewModel.onResponse " + response);
                if (response.body() != null) {
                    List<ScheduleItem> scheduleResponse = response.body();
                    scheduleItems.clear();
                    scheduleItems.addAll(scheduleResponse);
                }
                createScheduleViewModel.setIsLoading(false);
            }

            @Override
            public void onFailure(Call<List<ScheduleItem>> call, Throwable t) {
                createScheduleViewModel.setIsLoading(false);
                System.out.println("ScheduleViewModel.onFailure " + t.getLocalizedMessage());
            }
        });

    }
}
