package com.example.movieappgazi.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Movie implements Parcelable {

    private int id;
    private String original_title;
    private String release_date;
    private double vote_average;
    private String poster_path;
    private String backdrop_path;
    private String overview;
    private int[] genre_ids;

    public Movie(int id, String original_title, String release_date, double vote_average, String poster_path, String backdrop_path, String overview, int[] genre_ids) {
        this.id = id;
        this.original_title = original_title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.genre_ids = genre_ids;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        original_title = in.readString();
        release_date = in.readString();
        vote_average = in.readDouble();
        poster_path = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        genre_ids = in.createIntArray();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(original_title);
        dest.writeString(release_date);
        dest.writeDouble(vote_average);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
        dest.writeString(overview);
        dest.writeIntArray(genre_ids);
    }
}
