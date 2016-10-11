package com.example.ekta.popularmovies.Fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ekta.popularmovies.Acitivities.MainActivity;
import com.example.ekta.popularmovies.Acitivities.MovieDetailActivity;
import com.example.ekta.popularmovies.Adapters.FavouriteAdapter;
import com.example.ekta.popularmovies.Model.Movie;
import com.example.ekta.popularmovies.R;
import com.example.ekta.popularmovies.Database.DbHelper;
import com.example.ekta.popularmovies.Utilities.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FavouriteFragment extends Fragment implements FavouriteAdapter.ClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private RecyclerView listMoviesHits;
    String image_whole_Url;
    public String overview;
    String image_post_URL;
    public ArrayList<Movie> listMovies = new ArrayList<>();
    public FavouriteAdapter adapterFavourite;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String id;
    DbHelper dbHelper;
    ArrayList<Movie> movieFromDatabase;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DbHelper(getActivity());
        View view = inflater.inflate(R.layout.fragment_popular_movie, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        listMoviesHits = (RecyclerView) view.findViewById(R.id.listMoviesHits);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        }
        listMoviesHits.setLayoutManager(gridLayoutManager);
        adapterFavourite = new FavouriteAdapter(getActivity());
        //    button= (Button) view.findViewById(R.id.addButton);
        adapterFavourite.setClickListener(this);
        listMoviesHits.setAdapter(adapterFavourite);
        movieFromDatabase = new ArrayList<Movie>();

        getMovies();
        //  sendJsonRequest();
        listMoviesHits.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                int lastFirstVisiblePosition = ((GridLayoutManager) listMoviesHits.getLayoutManager()).findFirstVisibleItemPosition();
                ((GridLayoutManager) listMoviesHits.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);


                final Movie movie = new Movie();


            }
        });
        return view;

    }

    public void setMovieListFavourite(ArrayList<Movie> listMovies) {
        this.listMovies = listMovies;
    }

    @Override
    public void itemClicked(View view, int position) {
        Intent i = new Intent(getActivity(), MovieDetailActivity.class);
        Movie currentMovie = listMovies.get(position);
        id = currentMovie.getStringid();
        String name = currentMovie.getTitle();
        Movie movie;
        movie = dbHelper.getMovieFromDatabase(id);
        String title = movie.getTitle();
        String urlSelf = movie.getUrlSelf();
        String coverImage = movie.getCoverImage();
        String audienceScore = movie.getAudienceScore();
        String popularity = movie.getPopularity();
        String tagLine = movie.getTagLine();
        String releaseDate = movie.getReleaseDateTheater();
        String duration = movie.getDuration();
        String genre = movie.getGenre();
        String overview = movie.getOverview();
if(MainActivity.twoPane==false) {
    i.putExtra("stringId", id);
    i.putExtra("title", title);
    i.putExtra("coverImage", coverImage);
    i.putExtra("urlSelf", urlSelf);
    i.putExtra("audienceScore", audienceScore);
    i.putExtra("popularity", popularity);
    i.putExtra("tagLine", tagLine);
    i.putExtra("releaseDate", releaseDate);
    i.putExtra("duration", duration);
    i.putExtra("genre", genre);
    i.putExtra("overview", overview);
    i.putExtra("fragment", "favourite");
    startActivity(i);

}else{  movie = this.listMovies.get(position);
    Bundle bundle= new Bundle();
    Fragment movieDetail = new MovieDetailFragment();
    bundle.putString("stringId", movie.getStringid());
    bundle.putString("urlSelf", movie.getUrlSelf());
    bundle.putString("coverImage", coverImage);
    bundle.putString("audienceScore", audienceScore);
    bundle.putString("popularity", popularity);
    bundle.putString("tagLine", tagLine);
    bundle.putString("releaseDate", releaseDate);
    bundle.putString("duration", duration);
    bundle.putString("genre", genre);
    bundle.putString("overview", overview);
    bundle.putString("fragment", "favourite");
    movieDetail.setArguments(bundle);
    getActivity().getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.movie_detail_container2, movieDetail)
            .commit();}
onStart();


        onStart();
    }

    private void getMovies() {

        ArrayList<Movie> list = new ArrayList<Movie>();
        list = dbHelper.getAllComments();
        adapterFavourite.setMovieList(list);
        setMovieListFavourite(list);

    }


    @Override
    public void onResume() {
        super.onResume();
        getMovies();
        adapterFavourite.notifyDataSetChanged();
    }
}