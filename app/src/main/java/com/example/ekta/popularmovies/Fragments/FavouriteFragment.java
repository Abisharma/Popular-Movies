package com.example.ekta.popularmovies.Fragments;


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
import android.widget.Toast;

import com.example.ekta.popularmovies.Acitivities.DetailBoxOfficeActivity;
import com.example.ekta.popularmovies.Adapters.FavouriteAdapter;
import com.example.ekta.popularmovies.Model.Movie;
import com.example.ekta.popularmovies.R;
import com.example.ekta.popularmovies.Utilities.DbHelper;
import com.example.ekta.popularmovies.Utilities.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import javax.sql.DataSource;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment implements FavouriteAdapter.ClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    String image_whole_Url;
    public  String overview;
    String image_post_URL;
    public ArrayList<Movie> listMovies = new ArrayList<>();
    public FavouriteAdapter adapterFavourite;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private int previousTotal = 0;
    private boolean loading = true;
    private String mParam2;
    String id;
    DbHelper dbHelper;
    private int visibleThreshold = 4;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    ArrayList<Movie> movieFromDatabase;
    GridLayoutManager gridLayoutManager;
    public FavouriteFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<Movie> movieList = new ArrayList<>();
    public DataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (savedInstanceState != null) {
            listMovies = savedInstanceState.getParcelableArrayList("moviesList");
            previousTotal = savedInstanceState.getInt("previousTotal");
            firstVisibleItem = savedInstanceState.getInt("firstVisibleItem");
            visibleItemCount = savedInstanceState.getInt("visibleItemCount");
            totalItemCount = savedInstanceState.getInt("totalItemCount");
            loading = savedInstanceState.getBoolean("loading");
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment   // Inflate the layout for this fragment
        dbHelper=new DbHelper(getActivity());
        View view=inflater.inflate(R.layout.fragment_popular_movie, container, false);
         gridLayoutManager=   new GridLayoutManager(getActivity(),2);
        recyclerView = (RecyclerView) view.findViewById(R.id.listMoviesHits);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        }
        recyclerView.setLayoutManager(gridLayoutManager);
        adapterFavourite =new FavouriteAdapter(getActivity());
        //button= (Button) view.findViewById(R.id.addButton);
        adapterFavourite.setClickListener(this);
        recyclerView.setAdapter(adapterFavourite);
        movieFromDatabase= new ArrayList<Movie>();

        getMovies();
        //  sendJsonRequest();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();


                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;

                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {

                    loading = true;
                }
            }
        });

        return view;


    }
    public void setMovieListFavourite(ArrayList<Movie> listMovies) {

        this.listMovies = listMovies;
        //  notifyItemChanged(0, listMovies.size());
    }
    @Override
    public void itemClicked(View view, int position) {
        Intent i = new Intent(getActivity(),DetailBoxOfficeActivity.class);
        Movie currentMovie = listMovies.get(position);
        int id1=0;
        id=currentMovie.getStringid();
        String name=currentMovie.getTitle();
        Toast.makeText(getActivity(),"position is "+name,Toast.LENGTH_SHORT).show();
        Movie movie;
        movie=dbHelper.getMovieFromDatabase(id);
        String title= movie.getTitle();


        String urlSelf=movie.getUrlSelf();
        String coverImage=movie.getCoverImage();
        String audienceScore=movie.getAudienceScore();
        String popularity=movie.getPopularity();
        String tagLine=movie.getTagLine();
        String releaseDate=movie.getReleaseDateTheater();
        String duration=movie.getDuration();
        String genre=movie.getGenre();
        String overview= movie.getOverview();


        i.putExtra("stringId",id);
        i.putExtra("title", title);
        i.putExtra("coverImage", coverImage);
        i.putExtra("urlSelf", urlSelf);
        i.putExtra("audienceScore", audienceScore);
        i.putExtra("popularity", popularity);
        i.putExtra("tagLine", tagLine);
        i.putExtra("releaseDate", releaseDate);
        i.putExtra("duration", duration);
        i.putExtra("genre",genre);
        i.putExtra("overview",overview);
        i.putExtra("fragment","favourite");
        startActivity(i);

    }
    private void getMovies(){

        ArrayList<Movie> list = new ArrayList<Movie>();
        list= dbHelper.getAllComments();
        adapterFavourite.setMovieList(list);
        setMovieListFavourite(list);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("moviesList", listMovies);
        outState.putInt("previousTotal", previousTotal);
        outState.putInt("firstVisibleItem", firstVisibleItem);
        outState.putInt("visibleItemCount", visibleItemCount);
        outState.putInt("totalItemCount", totalItemCount);
        outState.putBoolean("loading", loading);
    }


    @Override
    public void onResume() {
        super.onResume();
        getMovies();
        adapterFavourite.notifyDataSetChanged();
    }
}
