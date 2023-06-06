package com.example.movieappgazi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieappgazi.databinding.MovieDetailBinding;
import com.example.movieappgazi.utils.Credentials;

public class DetailActivity extends AppCompatActivity {

    private MovieDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =  MovieDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle bundle = getIntent().getExtras();

        String mTitle = bundle.getString("title");
        String mPoster = bundle.getString("poster");
        String mOverView = bundle.getString("overview");
        double mRating = bundle.getDouble("rating");

        Glide.with(this).load( Credentials.BASE_IMAGE_URL + mPoster).into(binding.posterImage);
        binding.mRating.setText(Double.toString(mRating) + " / 10");
        binding.mTitle.setText(mTitle);
        binding.movervieTv.setText(mOverView);
    }
}