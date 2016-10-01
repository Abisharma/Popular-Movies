package com.example.ekta.popularmovies.Fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import static com.example.ekta.popularmovies.Utilities.Constants.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.ekta.popularmovies.Model.Movie;
import com.example.ekta.popularmovies.Adapters.MovieDetailAdapter;
import com.example.ekta.popularmovies.R;
import com.example.ekta.popularmovies.Utilities.VolleySingleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MovieDetailFragment extends Fragment {
    public ImageLoader imageLoader;
    public VolleySingleton volleySingleton;
    private Movie movie;
    public RequestQueue requestQueue;
    int duration = 0;
    String urlSelf;
    TextView tvDuration;
    String imagePostUrl = "";
    ImageView itemIcon2;
    String movieID;
    String fragmentValue;
    ImageView image;
    String imageString;
    String genres = "";
    String tagline = "";
    String vote_average = "";
    int hours = 0;
    String coverImage;
    String releaseDate, overview;
    String titleStr = "";
    int minutes = 0;
    String popularity = "";
    Movie movieInfo;
    String DurationString;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Activity activity;
    ImageView movieImage;
    String dataShare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_deatil, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_movie_detail);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager = new LinearLayoutManager(activity);
        movie = new Movie();
        volleySingleton = VolleySingleton.getInstance(getActivity());
        requestQueue = volleySingleton.getmRequestQueue();
        movieImage = (ImageView) view.findViewById(R.id.ivMovieImage);
        imageLoader = volleySingleton.getmImageLoader();
        movieID = getArguments().getString("stringId");
        urlSelf = getArguments().getString("urlSelf");
        fragmentValue = getArguments().getString("fragment");
        sendjsonRequest(movieID);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    int imageRequest(String imageString) {

        Glide.clear(movieImage);
        Glide
                .with(this)
                .load(imageString)
                .placeholder(android.R.color.transparent)
                .crossFade()
                .into(movieImage);

        return 1;
    }

    public void sendjsonRequest(final String id) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET
                , DATA_REQUEST_PREURL + id + API_KEY

                , new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                movieInfo = parseJsonResponse(response);
                mAdapter = new MovieDetailAdapter(movie,getActivity());
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


        requestQueue.add(request);


    }

    public Movie parseJsonResponse(JSONObject response) {

        if (response == null || response.length() == 0)
            return null;
        if (response != null && response.length() > 0) {
            try {


                titleStr = response.getString("original_title");
                imagePostUrl = response.getString("backdrop_path");
                coverImage = COVER_INMAGE;


                tagline = response.getString("tagline");

                duration = Integer.parseInt(response.getString("runtime"));
                releaseDate = response.getString("release_date");

                overview = response.getString("overview");

                final int durationInMin = duration;

                popularity = response.getString("popularity");

                vote_average = response.getString("vote_average");
                hours = durationInMin / 60;
                minutes = durationInMin % 60;
                DurationString = hours + " hr " + minutes + " min";


                imageString= COVER_INMAGE + imagePostUrl;

                final JSONArray genreArray = response.getJSONArray("genres");
                for (int i = 0; i < genreArray.length(); i++) {
                    String genre = genreArray.getJSONObject(i).getString("name");
                    if (i != genreArray.length() - 1)
                        genres += genre + ", ";
                    else
                        genres += genre + ".";
                }
                int a = imageRequest(imageString);
                movie.setStringid(movieID);
                movie.setTitle(titleStr);
                movie.setCoverImage(imageString);
                movie.setUrlSelf(urlSelf);
                movie.setTagLine(tagline);
                movie.setAudienceScore(vote_average);
                movie.setPopularity(popularity);
                movie.setReleasedate(releaseDate);
                movie.setDuration(DurationString);
                movie.setGenre(genres);
                movie.setOverview(overview);

            } catch (JSONException e) {
            }


        }
        return movie;
    }



}