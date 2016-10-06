package com.example.ekta.popularmovies.Acitivities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ekta.popularmovies.Fragments.MovieDetailFragment;
import com.example.ekta.popularmovies.R;

public class DetailBoxOfficeActivity extends AppCompatActivity {

    Bundle bundle;
    Fragment movieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_box_office);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        bundle = getIntent().getExtras();
        movieDetail = new MovieDetailFragment();
        movieDetail.setArguments(bundle);
        Intent i = getIntent();
        String s = i.getStringExtra("stringId");
        Toast.makeText(this, "id is " + s, Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_container, movieDetail)
                .commit();

    }
}