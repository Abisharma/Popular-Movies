package com.example.ekta.popularmovies.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ekta.popularmovies.fragments.MovieDetailFragment;
import com.example.ekta.popularmovies.R;

public class MovieDetailActivity extends AppCompatActivity {


    Bundle bundle;
    Fragment movieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        bundle = getIntent().getExtras();
        movieDetail = new MovieDetailFragment();
        movieDetail.setArguments(bundle);
        Intent i = getIntent();
        String s = i.getStringExtra("stringId");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_container, movieDetail)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
