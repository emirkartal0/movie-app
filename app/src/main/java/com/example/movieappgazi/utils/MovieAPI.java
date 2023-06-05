package com.example.movieappgazi.utils;

import com.example.movieappgazi.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieAPI {

    @GET("movie/popular")
    Call<MovieSearchResponse> fetchMovies(
            @Query("api_key") String key,
            @Query("page") String page
    );

    @GET("search/movie")
    Call<MovieSearchResponse> queryMovies(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") String page
    );
}
