package com.example.movieappgazi.services;

import com.example.movieappgazi.request.Servicey;
import com.example.movieappgazi.response.MovieSearchResponse;
import com.example.movieappgazi.utils.Credentials;
import com.example.movieappgazi.utils.MovieAPI;

import retrofit2.Call;

public class MovieService {

    private static final MovieService shared = new MovieService();

    private MovieService() { }

    public static MovieService getInstance() {
        return shared;
    }

    public Call<MovieSearchResponse> fetchMovies() {
        MovieAPI movieAPI = Servicey.getMovieAPI();
        Call<MovieSearchResponse> responseCall = movieAPI
                .fetchMovies(
                        Credentials.API_KEY,
                        "1"
                );
        return responseCall;
    }

    public Call<MovieSearchResponse> queryMovies(String q) {
        MovieAPI movieAPI = Servicey.getMovieAPI();
        Call<MovieSearchResponse> responseCall = movieAPI
                .queryMovies(
                        Credentials.API_KEY,
                        q,
                        "1"
                );
        return responseCall;
    }
}
