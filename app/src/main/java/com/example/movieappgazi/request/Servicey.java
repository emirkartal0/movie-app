package com.example.movieappgazi.request;

import com.example.movieappgazi.utils.Credentials;
import com.example.movieappgazi.utils.MovieAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicey {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Credentials.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static MovieAPI movieAPI = retrofit.create(MovieAPI.class);

    public static MovieAPI getMovieAPI() {
        return movieAPI;
    }
}
