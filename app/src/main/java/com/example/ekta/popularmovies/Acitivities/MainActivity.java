package com.example.ekta.popularmovies.Acitivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ekta.popularmovies.Fragments.PopularMovieFragment;
import com.example.ekta.popularmovies.R;
import com.example.ekta.popularmovies.Fragments.TopRatedFagment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int a= sortOrder();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
       int a= sortOrder();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public int sortOrder()
    {

        SharedPreferences sharePrefs= PreferenceManager.getDefaultSharedPreferences(this);

        String option=  sharePrefs.getString(getString(R.string.pref_sort),getString(R.string.pref_default));
        PopularMovieFragment fragment = PopularMovieFragment.newInstance("", "");
        TopRatedFagment fragment2= TopRatedFagment.newInstance("","");
        setContentView(R.layout.activity_main);

        if(option.equals("Popular"))
            getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment2).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        return 1;
    }
}

