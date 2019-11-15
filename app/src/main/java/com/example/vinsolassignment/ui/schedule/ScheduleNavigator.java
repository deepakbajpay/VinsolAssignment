package com.example.vinsolassignment.ui.schedule;

import java.util.Calendar;
import java.util.Date;

public interface ScheduleNavigator {
    void createNewSchedule(Calendar currentDate);

    void onDatePickerClick(Calendar currentDate);
}
