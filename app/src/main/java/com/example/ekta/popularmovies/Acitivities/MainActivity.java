package com.example.ekta.popularmovies.Acitivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ekta.popularmovies.Fragments.PopularMovieFragment;
import com.example.ekta.popularmovies.R;

public class MainActivity extends AppCompatActivity {
    String option = "";
    PopularMovieFragment fragment;
    SharedPreferences sharePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharePrefs = PreferenceManager.getDefaultSharedPreferences(this);

        option = sharePrefs.getString(getString(R.string.pref_sort), getString(R.string.pref_default));

        setContentView(R.layout.activity_main);

      //  if (savedInstanceState == null) {
       //     if (option.equals(R.string.pref_default)) {
                fragment = PopularMovieFragment.newInstance("", "");
                getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
       //     } else {
       //         fragment = PopularMovieFragment.newInstance("1", "");
       //         getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
      //      }
        //}
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(option + " "+this.getString(R.string.movies));


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
