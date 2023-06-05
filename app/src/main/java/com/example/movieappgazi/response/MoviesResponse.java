package com.example.movieappgazi.response;

import com.example.movieappgazi.models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviesResponse {

    @SerializedName("results")
    @Expose
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    @Override
    public String toString() {
        return "MoviesResponse{" +
                "movie=" + movie +
                '}';
    }
}
