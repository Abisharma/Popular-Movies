package com.example.ekta.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import static com.example.ekta.popularmovies.utilities.Constants.*;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.ekta.popularmovies.model.Movie;
import com.example.ekta.popularmovies.R;
import com.example.ekta.popularmovies.utilities.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by Ekta on 29-09-2016.
 */
public class MovieDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Movie movie;
    private ArrayList<String> trailerInfo = new ArrayList<>();
    private ArrayList<String> reviewsInfo = new ArrayList<>();
    private LayoutInflater layoutInflater;
    android.content.Context adapterContext;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    int a;
    private String dataZero;

    public MovieDetailAdapter(Movie movie, Context context) {
        adapterContext = context;
        if (adapterContext != null)
            layoutInflater = LayoutInflater.from(adapterContext);
        this.movie = movie;
    }

    public MovieDetailAdapter(Movie movie, ArrayList<String> trailerInfo, ArrayList<String> reviewsInfo, Context context) {
        adapterContext = context;
        this.movie = movie;
        if (adapterContext != null)
        layoutInflater = LayoutInflater.from(adapterContext);
        volleySingleton = VolleySingleton.getInstance(adapterContext);
        imageLoader = volleySingleton.getmImageLoader();
        this.reviewsInfo = reviewsInfo;

        this.trailerInfo = trailerInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        if (viewType == 0) {
            View view = layoutInflater.inflate(R.layout.movie_detail_holder, parent, false);
            viewHolder = new MovieDetailViewHolder(view);
            return viewHolder;
        }

        if (viewType == 1) {
            View view = layoutInflater.inflate(R.layout.movie_trailerinfo_holder, parent, false);
            viewHolder = new TrailersViewHolder(view);

            return viewHolder;
        }

        if (viewType == 2) {
            View view = layoutInflater.inflate(R.layout.movie_reviewinfo_holder, parent, false);
            viewHolder = new ReviewsViewHolder(view);
            a = 0;
            return viewHolder;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {

            case 0:
                ((MovieDetailViewHolder) holder).movieName.setText(movie.getTitle());
               if (!movie.getTagLine().equals("")) {
                    ((MovieDetailViewHolder) holder).movieTagLine.setText("\" " + movie.getTagLine() + " \"");
                } else if (movie.getTagLine().equals("")) {
                    ((MovieDetailViewHolder) holder).movieTagLine.setVisibility(View.GONE);
                }

                ((MovieDetailViewHolder) holder).movieReleaseDate.setText(movie.getReleasedate());
                String durationInMin = movie.getDuration();
                ((MovieDetailViewHolder) holder).movieDuration.setText(durationInMin);
                ((MovieDetailViewHolder) holder).movieGenre.setText(movie.getGenre());
               ((MovieDetailViewHolder) holder).moviePopularity.setText(String.format("%.1f", Float.parseFloat(movie.getPopularity())) + "");
                ((MovieDetailViewHolder) holder).movieRating.setText(String.format((movie.getAudienceScore()) + ""));
                ((MovieDetailViewHolder) holder).movieSynopsis.setText(movie.getOverview());
                break;
            case 1:


                final String[] data = trailerInfo.get(position - 1).split(",");

                dataZero = data[0];
                String coverImage = YOUTUBE_IMAGE_PREURL + data[0] + YOUTUBE_IMAGE_POSTURL;
                if (coverImage != null) {
                    imageLoader.get(coverImage, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                            ((TrailersViewHolder) holder).trailerImageView.setImageBitmap(response.getBitmap());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                }

                ((TrailersViewHolder) holder).trailerTitle.setText(data[1]);
                ((TrailersViewHolder) holder).trailerTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapterContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_PREURL + data[0])));
                    }
                });
                ((TrailersViewHolder) holder).trailerImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapterContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_PREURL + data[0])));
                    }
                });
                break;

            case 2:
                String author = (reviewsInfo.get(position - 1 - trailerInfo.size())
                        .substring(0, reviewsInfo.get(position - 1 - trailerInfo.size()).indexOf(",")));
                ((ReviewsViewHolder) holder).reviwerDesc
                        .setText(reviewsInfo.get(position - 1 - trailerInfo.size())
                                .substring(reviewsInfo.get(position - 1 - trailerInfo.size()).indexOf(",") + 1));
                ((ReviewsViewHolder) holder).reviwerName.setText(author);


        }
    }

    @Override
    public int getItemCount() {
        return 1 + trailerInfo.size() + reviewsInfo.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        if (position > 0 && position <= trailerInfo.size())
            return 1;
        if (position > trailerInfo.size() && position <= trailerInfo.size() + reviewsInfo.size())
            return 2;
        return 333;
    }


    class MovieDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView movieName, movieTagLine, movieReleaseDate, movieDuration, movieGenre, moviePopularity, movieSynopsis, movieRating, movieLanguage;
        public ImageView movieImage;

        public MovieDetailViewHolder(View itemView) {
            super(itemView);
            movieImage = (ImageView) itemView.findViewById(R.id.movieImage);
            movieName = (TextView) itemView.findViewById(R.id.tvMovieTitle);
            movieTagLine = (TextView) itemView.findViewById(R.id.tvMovieTagLine);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.tvMovieReleaseDate);
            movieDuration = (TextView) itemView.findViewById(R.id.tvMovieDuration);
            movieGenre = (TextView) itemView.findViewById(R.id.tvMovieGenre);
            moviePopularity = (TextView) itemView.findViewById(R.id.tvMoviePopularity);
            movieRating = (TextView) itemView.findViewById(R.id.tvMovieRating);
            movieSynopsis = (TextView) itemView.findViewById(R.id.tvMovieSynopsis);

        }
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder {

        private TextView trailerTitle;
        private ImageView trailerImageView;
        private RelativeLayout trailer;


        public TrailersViewHolder(View itemView) {
            super(itemView);


            trailerTitle = (TextView) itemView.findViewById(R.id.ivTrailerTitle);
            trailerImageView = (ImageView) itemView.findViewById(R.id.ivTrailerImage);

        }
    }


    class ReviewsViewHolder extends RecyclerView.ViewHolder {

        private TextView reviwerName, reviwerDesc;


        public ReviewsViewHolder(View itemView) {
            super(itemView);


            reviwerName = (TextView) itemView.findViewById(R.id.tvReviewName);
            reviwerDesc = (TextView) itemView.findViewById(R.id.tvReviewDesc);

        }
    }

}

