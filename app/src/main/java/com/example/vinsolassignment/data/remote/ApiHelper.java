package com.example.vinsolassignment.data.remote;

import com.example.vinsolassignment.data.model.ScheduleItem;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Mehul on 11-01-2017.
 */
public interface ApiHelper {
    @GET
    Call<List<ScheduleItem>> getSchedules(@Url String url);
}
