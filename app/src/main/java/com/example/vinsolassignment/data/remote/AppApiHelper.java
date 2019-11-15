package com.example.vinsolassignment.data.remote;

import com.example.vinsolassignment.data.model.ScheduleItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Call;

public class AppApiHelper implements ApiHelper {

    private APIClient mApiHeader;

    @Inject
    public AppApiHelper(APIClient apiHeader) {
        mApiHeader = apiHeader;
    }


    @Override
    public Call<List<ScheduleItem>> getSchedules(String url) {
      /*  try {
            return mApiHeader.getWebServices().getSchedules(url).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return null;
    }
}
