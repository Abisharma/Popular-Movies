package com.example.ekta.popularmovies.Acitivities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ekta.popularmovies.Fragments.MovieDeatilFragment;
import com.example.ekta.popularmovies.R;

public class MovieDetailActivity extends AppCompatActivity {


    Bundle bundle;
    Fragment movieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        bundle = getIntent().getExtras();
        movieDetail = new MovieDeatilFragment();
        movieDetail.setArguments(bundle);
        Intent i= getIntent();
        String s= i.getStringExtra("stringId");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_container, movieDetail)
                .commit();
    }
}
