package com.example.movieappgazi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Movie> movieList;

    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nav = findViewById(R.id.nav);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int itemId = item.getItemId();
                if (itemId == R.id.movies) {
                    Toast.makeText(MainActivity.this, "Movies", Toast.LENGTH_LONG).show();
                } else if (itemId == R.id.search) {
                    Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_LONG).show();
                    // Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    // startActivity(intent);

                } else if (itemId == R.id.favorites) {
                    Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_LONG).show();
                }

                return true;
            }
        });


        movieList = new ArrayList<>();
        fetchMovies();


    }

    private void fetchMovies() {

        MovieAPI movieAPI = Servicey.getMovieAPI();
        Call<MovieSearchResponse> responseCall = movieAPI
                .searchMovie(
                        Credentials.API_KEY,
                        "1"
                );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, retrofit2.Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    Log.v("Tag","the response " + response.body().toString());

                    List<Movie> fetchedMovies = new ArrayList<>(response.body().getMovies());

                    MovieAdapter movieAdapter = new MovieAdapter(MainActivity.this, fetchedMovies);
                    recyclerView.setAdapter(movieAdapter);

                    // movieList = fetchedMovies;
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