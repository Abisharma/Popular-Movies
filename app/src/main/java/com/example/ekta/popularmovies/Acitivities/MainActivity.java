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

import com.example.ekta.popularmovies.Fragments.PopularMovieFragment;
import com.example.ekta.popularmovies.R;
import com.example.ekta.popularmovies.Utilities.SlidingTabLayout;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {
    String option = "";
    PopularMovieFragment fragment;
    SharedPreferences sharePrefs;
    private SlidingTabLayout mTabs;

    public static final int MOVIES_SEARCH_RESULTS = 0;

    public static final int MOVIES_HITS = 1;

    public static final int MOVIES_UPCOMING = 2;
    public MaterialTabHost tabHost;
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharePrefs = PreferenceManager.getDefaultSharedPreferences(this);
        option = sharePrefs.getString(getString(R.string.pref_sort), getString(R.string.pref_default));
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            fragment = PopularMovieFragment.newInstance("", "");
            getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout, fragment).commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(option + " " + this.getString(R.string.movies));


        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);

        viewPager = (ViewPager) findViewById(R.id.pager);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());


        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                tabHost.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < adapter.getCount(); i++) {

            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this));

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

    @Override
    public void onTabSelected(MaterialTab tab) {

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }


    class MyPagerAdapter extends FragmentStatePagerAdapter {

        String[] tabText =   getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            android.support.v4.app.Fragment fragment = null;

            switch (position) {

                case 0:
                    fragment = PopularMovieFragment.newInstance("", "");
                    break;
                case 1:
                    fragment = PopularMovieFragment.newInstance("", "");
                    break;

            }

            return fragment;

        }

        public CharSequence getPageTitle(int position) {
            return tabText[position];

        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}


