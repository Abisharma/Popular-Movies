package com.example.ekta.popularmovies.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ekta.popularmovies.Acitivities.MovieDetailActivity;
import static com.example.ekta.popularmovies.Utilities.Constants.*;
import com.example.ekta.popularmovies.Utilities.EndlessRecyclerOnScrollListener;
import com.example.ekta.popularmovies.Model.Movie;
import com.example.ekta.popularmovies.Adapters.PopularMovieAdapter;
import com.example.ekta.popularmovies.R;
import com.example.ekta.popularmovies.Utilities.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PopularMovieFragment extends Fragment   implements PopularMovieAdapter.ClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public VolleySingleton volleySingleton;
    public RequestQueue requestQueue;
    public String title;
    public  String release_date;
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    String image_whole_Url;
    int pages;
    ProgressBar pbLoader;
    Movie movie;
    String image_post_URL;
    int pageCount=1;
    public ArrayList<Movie> listMovies = new ArrayList<>();
    public PopularMovieAdapter popularMovieAdapter;
    Context context;
    public PopularMovieFragment() {
        // Required empty public constructor
    }

    public static PopularMovieFragment newInstance(String param1, String param2) {
        PopularMovieFragment fragment = new PopularMovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

        volleySingleton=VolleySingleton.getInstance(getActivity());
        requestQueue= volleySingleton.getmRequestQueue();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void sendJsonRequest(int pageCount)
    {
        final String sortBy;
        pbLoader.setVisibility(View.VISIBLE);
        if(mParam1.equals("0"))
             sortBy=POPULAR;
        else sortBy= TOP_RATED;
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET
                , DATA_REQUEST_PREURL +sortBy+API_KEY + "&page="+pageCount
                , new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                pbLoader.setVisibility(View.GONE);
                listMovies = parseJsonResponse(response);
                popularMovieAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
    }

    public ArrayList<Movie> parseJsonResponse(JSONObject response) {

            if (response == null || response.length() == 0)
                return null;
        if(response != null && response.length() > 0){
            try {
                pages= response.getInt("total_pages");
                JSONArray arrayMovieResults = response.getJSONArray("results");
                for (int i1 = 0; i1<arrayMovieResults.length(); i1++) {

                    JSONObject currentMovie = arrayMovieResults.getJSONObject(i1);
                    String sId = currentMovie.getString("id");
                    title = currentMovie.getString("title");
                    image_post_URL = currentMovie.getString("poster_path");
                    image_whole_Url = IMAGE_PREURL + image_post_URL;
                    //appending all the movies which we are getting one by one
                    Movie movie = new Movie();
                    movie.setStringid(sId);
                    movie.setUrlSelf(image_whole_Url);
                    listMovies.add(movie);
                }
            } catch (JSONException e){}
        }
        return listMovies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_popular_movie, container, false);
        GridLayoutManager gridLayoutManager=   new GridLayoutManager(getActivity(),2);
        recyclerView = (RecyclerView) view.findViewById(R.id.listMoviesHits);
        recyclerView.setHasFixedSize(true);
        pbLoader = (ProgressBar) view.findViewById(R.id.pbLoader);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        }
        recyclerView.setLayoutManager(gridLayoutManager);
        popularMovieAdapter=new PopularMovieAdapter(listMovies,getActivity());
        popularMovieAdapter.setClickListener(this);
        recyclerView.setAdapter(popularMovieAdapter);

        sendJsonRequest(1);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                int lastFirstVisiblePosition=((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                ( (GridLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
                if (current_page==1)
                    sendJsonRequest(1);
                if(pageCount<pages) {
                    pageCount++;
                    sendJsonRequest(pageCount);
                }

            }
        });
        return view;

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void itemClicked(View view,int position) {

        Intent i = new Intent(getActivity(),MovieDetailActivity.class);
        movie=this.listMovies.get(position);
        i.putExtra("stringId",movie.getStringid());
        i.putExtra("urlSelf",movie.getUrlSelf());
        startActivity(i);
    }

}
