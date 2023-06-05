package com.example.movieappgazi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.movieappgazi.models.Movie;
import com.example.movieappgazi.request.Servicey;
import com.example.movieappgazi.response.MovieSearchResponse;
import com.example.movieappgazi.utils.Credentials;
import com.example.movieappgazi.utils.MovieAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Movie> movieList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryMovies(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")) {
                    fetchMovies();
                }
                return false;
            }
        });

        movieList = new ArrayList<>();
        fetchMovies();
    }

    private void fetchMovies() {

        MovieAPI movieAPI = Servicey.getMovieAPI();
        Call<MovieSearchResponse> responseCall = movieAPI
                .fetchMovies(
                        Credentials.API_KEY,
                        "1"
                );
        loadData(responseCall);
    }

    private void queryMovies(String q) {

        MovieAPI movieAPI = Servicey.getMovieAPI();
        Call<MovieSearchResponse> responseCall = movieAPI
                .queryMovies(
                        Credentials.API_KEY,
                        q,
                        "1"
                );
        loadData(responseCall);
    }

    private void loadData(Call<MovieSearchResponse> responseCall) {
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, retrofit2.Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    Log.v("Tag","the response " + response.body().toString());

                    List<Movie> fetchedMovies = new ArrayList<>(response.body().getMovies());

                    MovieAdapter movieAdapter = new MovieAdapter(MainActivity.this, fetchedMovies);
                    recyclerView.setAdapter(movieAdapter);
                } else {
                    Log.v("Tag", "Error " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }
}