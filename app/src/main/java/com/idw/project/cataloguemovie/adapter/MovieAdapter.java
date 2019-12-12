package com.idw.project.cataloguemovie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.idw.project.cataloguemovie.R;
import com.idw.project.cataloguemovie.config.Config;
import com.idw.project.cataloguemovie.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> dataList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Movie item);
    }

    public MovieAdapter(ArrayList<Movie> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View view = layoutInflater.inflate(R.layout.movie_item, viewGroup, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder movieViewHolder, final int i) {
        movieViewHolder.bind(dataList.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() :0;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_poster_movie;
        TextView tv_title_movie, tv_release_date_movie, tv_description_movie, tv_rb_movie;
        CardView cv_movie;
        RatingBar rb_movie;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster_movie = itemView.findViewById(R.id.iv_poster_movie);
            tv_title_movie = itemView.findViewById(R.id.tv_title_movie);
            tv_release_date_movie = itemView.findViewById(R.id.tv_release_date_movie);
            rb_movie = itemView.findViewById(R.id.rb_movie);
            tv_rb_movie = itemView.findViewById(R.id.tv_rb_movie);
            tv_description_movie = itemView.findViewById(R.id.tv_description_movie);
            cv_movie = itemView.findViewById(R.id.cv_movie);
        }

        void bind(final Movie item, final OnItemClickListener listener){
            tv_title_movie.setText(item.getTitle());
            tv_release_date_movie.setText(item.getReleaseDate());
            tv_description_movie.setText(item.getOverview());
            tv_rb_movie.setText(String.valueOf(item.getVoteAverage()));
            rb_movie.setRating(Float.parseFloat(String.valueOf(item.getVoteAverage())));

            Picasso.with(itemView.getContext())
                    .load(Config.IMAGE_W342+item.getPosterPath())
                    .fit()
                    .centerCrop()
                    .into(iv_poster_movie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
