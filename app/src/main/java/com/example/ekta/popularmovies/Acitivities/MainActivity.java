package com.example.ekta.popularmovies.Acitivities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.ekta.popularmovies.Fragments.FavouriteFragment;
import com.example.ekta.popularmovies.Fragments.MovieDetailFragment;
import com.example.ekta.popularmovies.Fragments.PopularMovieFragment;
import com.example.ekta.popularmovies.R;

public class MainActivity extends AppCompatActivity{
    String option = "";

    SharedPreferences sharePrefs;

    public static boolean twoPane=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharePrefs = PreferenceManager.getDefaultSharedPreferences(this);
        option = sharePrefs.getString(getString(R.string.pref_sort), getString(R.string.pref_default));
        setContentView(R.layout.activity_main);
       Boolean fav= sharePrefs.getBoolean(getString(R.string.Favourite),false);
        Toast.makeText(this,fav.toString(),Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
if(fav==false) {
    getSupportActionBar().setTitle(option + " " + this.getString(R.string.movies));
    PopularMovieFragment popularMovieFragment = new PopularMovieFragment();
    Intent i = getIntent();
    getSupportFragmentManager().beginTransaction()
            .replace(R.id.mainactivity_container, popularMovieFragment)
            .commit();
}
        else

{
    getSupportActionBar().setTitle(this.getString(R.string.Favourite) + " " +this.getString(R.string.movies) );
   FavouriteFragment favouriteFragment = new FavouriteFragment();
    Intent i = getIntent();
    getSupportFragmentManager().beginTransaction()
            .replace(R.id.mainactivity_container, favouriteFragment)
            .commit();
}
      if(findViewById(R.id.movie_detail_container2)!=null) {
            twoPane=true;
            String movieID="271110",urlSelf,fragment="popular";

                Bundle bundle1 =new Bundle();
                bundle1.putString("stringId", movieID);
                bundle1.putString("fragment", fragment);
                MovieDetailFragment movieDetail;
                movieDetail = new MovieDetailFragment();
                movieDetail.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container2, movieDetail)
                        .commit();

        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}


