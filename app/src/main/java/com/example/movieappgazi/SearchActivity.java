package com.example.movieappgazi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieappgazi.models.Movie;
import com.example.movieappgazi.request.Servicey;
import com.example.movieappgazi.response.MovieSearchResponse;
import com.example.movieappgazi.utils.Credentials;
import com.example.movieappgazi.utils.MovieAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends AppCompatActivity {


    private List<Movie> movieList;

    private RecyclerView recyclerView;
    private SearchView searchView;
    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_search);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);

        nav = findViewById(R.id.nav);

        nav.setSelectedItemId(R.id.search);

        Log.d("src act","search activity cagirdik");

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int itemId = item.getItemId();
                if (itemId == R.id.movies) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.search) {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryMovies(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        movieList = new ArrayList<>();


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

                    MovieAdapter movieAdapter = new MovieAdapter(SearchActivity.this, fetchedMovies);
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
