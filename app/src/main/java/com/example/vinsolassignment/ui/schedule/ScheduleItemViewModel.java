package com.example.vinsolassignment.ui.schedule;

import androidx.databinding.ObservableField;

import com.example.vinsolassignment.data.model.ScheduleItem;

import java.util.List;

public class ScheduleItemViewModel {
    public final ObservableField<String> startTime;
    public final ObservableField<String> endTime;
    public final ObservableField<String> description;
    public final ObservableField<List<String>> participants;

    public ScheduleItemViewModel(ScheduleItem scheduleItem) {
        this.startTime = new ObservableField<>(scheduleItem.getStartTime());
        this.endTime = new ObservableField<>(scheduleItem.getEndTime());
        this.description = new ObservableField<>(scheduleItem.getDescription());
        System.out.println("ScheduleItemViewModel.ScheduleItemViewModel "+description);
        this.participants = new ObservableField<List<String>>(scheduleItem.getParticipants());
    }


}
