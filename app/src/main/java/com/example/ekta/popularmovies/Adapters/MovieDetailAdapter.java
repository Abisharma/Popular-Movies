package com.example.ekta.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.ekta.popularmovies.Model.Movie;
import com.example.ekta.popularmovies.R;
import com.example.ekta.popularmovies.Utilities.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by Ekta on 29-09-2016.
 */
public class MovieDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Movie movie;
    private ArrayList<String> trailerInfo = new ArrayList<>();
    private ArrayList<String> reviewsInfo = new ArrayList<>();
    private LayoutInflater layoutInflater;
    android.content.Context contextt;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private String dataZero;
    int a;

    public MovieDetailAdapter(Movie movie, Context context) {
        contextt = context;
        layoutInflater = LayoutInflater.from(contextt);
        volleySingleton = VolleySingleton.getInstance(context);
        imageLoader = volleySingleton.getmImageLoader();
        // this.context = context;
        this.movie = movie;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;


        View view = layoutInflater.inflate(R.layout.movie_detail_holder, parent, false);
        viewHolder = new MovieDetailViewHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {


        ((MovieDetailViewHolder) holder).movieName.setText(movie.getTitle());
        if (!movie.getTagLine().equals("")) {
            ((MovieDetailViewHolder) holder).movieTagLine.setText("\" " + movie.getTagLine() + " \"");
        } else if (movie.getTagLine().equals("")) {
            ((MovieDetailViewHolder) holder).movieTagLine.setVisibility(View.GONE);
        }
        ((MovieDetailViewHolder) holder).movieReleaseDate.setText(movie.getReleasedate());//+ "(" + movie.getStatus() + ")");

        String durationInMin = movie.getDuration();

        ((MovieDetailViewHolder) holder).movieDuration.setText(durationInMin);

        ((MovieDetailViewHolder) holder).movieGenre.setText("Genre: " + movie.getGenre());
        //  ((MovieDetailViewHolder) holder).movieLanguage.setText(String.format("Language: " + movie.getLanguage()));
        ((MovieDetailViewHolder) holder).moviePopularity.setText(movie.getPopularity());
        ((MovieDetailViewHolder) holder).movieRating.setText(String.format((movie.getAudienceScore()) + ""));
        ((MovieDetailViewHolder) holder).movieSynopsis.setText(movie.getOverview());


    }

    @Override
    public int getItemCount() {
        return 1;
    }


    class MovieDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView movieName, movieTagLine, movieReleaseDate, movieDuration, movieGenre, moviePopularity, movieSynopsis, movieRating, movieLanguage;
        public ImageView movieImage;


        public MovieDetailViewHolder(View itemView) {
            super(itemView);
            // context = itemView.getContext();
            movieImage = (ImageView) itemView.findViewById(R.id.movieImage);
            movieName = (TextView) itemView.findViewById(R.id.tvMovieTitle);
            movieTagLine = (TextView) itemView.findViewById(R.id.tvMovieTagLine);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.tvMovieReleaseDate);
            movieDuration = (TextView) itemView.findViewById(R.id.tvMovieDuration);
            movieGenre = (TextView) itemView.findViewById(R.id.tvMovieGenre);
            moviePopularity = (TextView) itemView.findViewById(R.id.tvMoviePopularity);
            movieRating = (TextView) itemView.findViewById(R.id.tvMovieRating);
            movieSynopsis = (TextView) itemView.findViewById(R.id.tvMovieSynopsis);
            //    movieLanguage = (TextView) itemView.findViewById(R.id.tvMovieLanguage);
            // movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
        }


        public String getTitle(String title) {
            return title;
        }

    }
}
