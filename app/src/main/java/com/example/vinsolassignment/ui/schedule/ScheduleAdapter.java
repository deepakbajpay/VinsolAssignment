package com.example.vinsolassignment.ui.schedule;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinsolassignment.data.model.ScheduleItem;
import com.example.vinsolassignment.databinding.ScheduleAdapterItemBinding;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<ScheduleItem> scheduleItemList;

    public ScheduleAdapter(List<ScheduleItem> scheduleItemList) {
        this.scheduleItemList = scheduleItemList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleViewHolder(ScheduleAdapterItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {

        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return scheduleItemList.size();
    }

    public void clearItems() {
        scheduleItemList.clear();
    }

    public void addItems(List<ScheduleItem> scheduleItems) {
        scheduleItemList.addAll(scheduleItems);
        System.out.println("ScheduleAdapter.addItems " + scheduleItems.size());
        notifyDataSetChanged();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {
        ScheduleAdapterItemBinding mBinding;
        ScheduleItemViewModel scheduleItemViewModel;

        public ScheduleViewHolder(@NonNull ScheduleAdapterItemBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }

        public void onBind(int position) {
            final ScheduleItem scheduleItem = scheduleItemList.get(position);
            scheduleItemViewModel = new ScheduleItemViewModel(scheduleItem);
            mBinding.setViewModel(scheduleItemViewModel);

            mBinding.executePendingBindings();
        }
    }

}
