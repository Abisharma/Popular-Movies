package com.example.ekta.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.ekta.popularmovies.Model.Movie;
import com.example.ekta.popularmovies.R;
import com.example.ekta.popularmovies.Utilities.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by Ekta on 06-10-2016.
 */
public class FavouriteAdapter  extends RecyclerView.Adapter<FavouriteAdapter.ViewHolderFavourite>  {


    public VolleySingleton volleySingleton;
    public ImageLoader imageLoader;
    public ArrayList<Movie> listMovies = new ArrayList<>();
    private LayoutInflater layoutInflater;
    public Context context;
    public ClickListener clickListener;


    public FavouriteAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance(context);
        imageLoader = volleySingleton.getmImageLoader();
    }

    public void setMovieList(ArrayList<Movie> listMovies) {

        this.listMovies = listMovies;
        notifyItemChanged(0, listMovies.size());
    }

    @Override
    public ViewHolderFavourite onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.popular_movies, parent, false);
        ViewHolderFavourite viewHolder = new ViewHolderFavourite(view);


        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final FavouriteAdapter.ViewHolderFavourite holder, int position) {

        Movie currentMovie = listMovies.get(position);

        // holder.movieTitle.setText(currentMovie.getTitle());
        String url = currentMovie.getUrlSelf();
        if (url != null) {

            imageLoader.get(url, new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movieImage.setImageBitmap(response.getBitmap());
                }
            });

        }


    }


    public void setClickListener(ClickListener clickListener){

        this.clickListener=clickListener;
    }
    @Override
    public int getItemCount() {
        return listMovies.size();
    }



    class ViewHolderFavourite extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ImageView movieImage;
        // public TextView movieTitle;


        public ViewHolderFavourite(View itemView) {
            super(itemView);
            // context = itemView.getContext();
            itemView.setOnClickListener(this);
            movieImage = (ImageView) itemView.findViewById(R.id.movieImage);
            // movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
        }


        public String getTitle(String title){

            return title;
        }
        @Override
        public void onClick(View v) {
            //  Intent intent = new Intent(context, DeatilBoxOfficeActivity.class);

            // context.startActivity(intent);
            //  context.startActivity(new Intent(context, DeatilBoxOfficeActivity.class));
            String title;
            if(clickListener!=null){

                clickListener.itemClicked(v,getPosition());
            }

        }
    }

    public interface ClickListener{

        public void itemClicked(View view,int position);





    }
}

