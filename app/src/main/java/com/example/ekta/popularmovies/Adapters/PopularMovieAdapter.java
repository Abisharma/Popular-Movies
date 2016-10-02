package com.example.ekta.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.ekta.popularmovies.Model.Movie;
import com.example.ekta.popularmovies.R;
import com.example.ekta.popularmovies.Utilities.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by Ekta on 28-09-2016.
 */
public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.ViewHolderBoxOffice> {


    public VolleySingleton volleySingleton;
    public ImageLoader imageLoader;
    public ArrayList<Movie> listMovies = new ArrayList<>();
    private LayoutInflater layoutInflater;
    public Context context;
    public ClickListener clickListener;


    public PopularMovieAdapter(ArrayList<Movie> listMovies, Context context) {
        this.context = context;
        this.listMovies = listMovies;
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance(context);
        imageLoader = volleySingleton.getmImageLoader();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.popular_movies, parent, false);
        ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {

        Movie currentMovie = listMovies.get(position);
        final String url = currentMovie.getUrlSelf();
        if (url != null) {

            Glide.clear(holder.movieImage);
            Glide
                    .with(context)
                    .load(url)
                    .placeholder(android.R.color.transparent)
                    .crossFade()
                    .into(holder.movieImage);

        }
    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }


    class ViewHolderBoxOffice extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView movieImage;

        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            movieImage = (ImageView) itemView.findViewById(R.id.movieImage);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {

                clickListener.itemClicked(v, getPosition());
            }

        }
    }

    public interface ClickListener {
        void itemClicked(View view, int position);
    }
}