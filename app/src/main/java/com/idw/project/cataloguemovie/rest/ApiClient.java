package com.idw.project.cataloguemovie.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.idw.project.cataloguemovie.config.Config.ENDPOINT;

public class ApiClient {
    private static final String BASE_URL = ENDPOINT;
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
