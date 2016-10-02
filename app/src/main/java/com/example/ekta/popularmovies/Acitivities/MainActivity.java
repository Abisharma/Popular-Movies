package com.example.ekta.popularmovies.Acitivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ekta.popularmovies.Fragments.PopularMovieFragment;
import com.example.ekta.popularmovies.R;


public class MainActivity extends AppCompatActivity {

    SharedPreferences sharePrefs ;
    String option;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = new Bundle();
        sharePrefs = PreferenceManager.getDefaultSharedPreferences(this);
         option = sharePrefs.getString(getString(R.string.pref_sort), getString(R.string.pref_default));
        PopularMovieFragment fragment;
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            if (option.equals("Popular")) {
                fragment = PopularMovieFragment.newInstance("", "");
                bundle.putInt("sort_value", 0);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
            } else {

                    fragment = PopularMovieFragment.newInstance("", "");
                    bundle.putInt("sort_value", 1);
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();

            }
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("option", option);
        outState.putString("orientationChanged", "yes");

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

   public  int sortorder() {

        SharedPreferences sharePrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String option = sharePrefs.getString(getString(R.string.pref_sort), getString(R.string.pref_default));
        PopularMovieFragment fragment;
        setContentView(R.layout.activity_main);

        if (option.equals("Popular")) {
            fragment = PopularMovieFragment.newInstance("0", "");
            getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
        } else {
            fragment = PopularMovieFragment.newInstance("1", "");
            getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
return 1;
    }
}



