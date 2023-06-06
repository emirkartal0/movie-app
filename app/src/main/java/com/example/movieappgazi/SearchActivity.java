package com.example.movieappgazi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieappgazi.databinding.AvtivitySearchBinding;
import com.example.movieappgazi.models.Movie;
import com.example.movieappgazi.request.Servicey;
import com.example.movieappgazi.response.MovieSearchResponse;
import com.example.movieappgazi.services.MovieService;
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

    private AvtivitySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = AvtivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        binding.nav.setSelectedItemId(R.id.search);

        Log.d("src act","search activity cagirdik");

        binding.nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadData(MovieService.getInstance().queryMovies(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }


    private void loadData(Call<MovieSearchResponse> responseCall) {
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, retrofit2.Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    Log.v("Tag","the response " + response.body().toString());

                    List<Movie> fetchedMovies = new ArrayList<>(response.body().getMovies());

                    MovieAdapter movieAdapter = new MovieAdapter(SearchActivity.this, fetchedMovies);
                    binding.recyclerview.setAdapter(movieAdapter);
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
