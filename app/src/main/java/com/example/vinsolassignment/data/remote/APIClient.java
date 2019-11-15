package com.example.vinsolassignment.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mehul on 11-01-2017.
 */
public class APIClient {
    public static final String BASEURL = "http://fathomless-shelf-5846.herokuapp.com/api/schedule/";
    private static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private ApiHelper webServices;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true);

        return builder.readTimeout(30000, TimeUnit.SECONDS)
                .connectTimeout(30000, TimeUnit.SECONDS)
                .build();

    }

    public ApiHelper getWebServices() {
        if (webServices == null) {
            webServices = getClient().create(ApiHelper.class);
        }
        return webServices;
    }
}
