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
import com.idw.project.cataloguemovie.model.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvViewHolder> {

    private ArrayList<TvShow> dataList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TvShow item);
    }

    public TvShowAdapter(ArrayList<TvShow> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TvShowAdapter.TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View view = layoutInflater.inflate(R.layout.tvshow_item, viewGroup, false);

        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.TvViewHolder tvViewHolder, final int i) {
        tvViewHolder.bind(dataList.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() :0;
    }

    class TvViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_poster_tv;
        TextView tv_title_tv, tv_release_date_tv, tv_description_tv, tv_rb_tv;
        CardView cv_tv;
        RatingBar rb_tv;

        TvViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster_tv = itemView.findViewById(R.id.iv_poster_tv);
            tv_title_tv = itemView.findViewById(R.id.tv_title_tv);
            tv_release_date_tv = itemView.findViewById(R.id.tv_release_date_tv);
            rb_tv = itemView.findViewById(R.id.rb_tv);
            tv_rb_tv = itemView.findViewById(R.id.tv_rb_tv);
            tv_description_tv = itemView.findViewById(R.id.tv_description_tv);
            cv_tv = itemView.findViewById(R.id.cv_tv);
        }

        void bind(final TvShow item, final OnItemClickListener listener){
            tv_title_tv.setText(item.getOriginalName());
            tv_release_date_tv.setText(item.getFirstAirDate());
            tv_description_tv.setText(item.getOverview());
            tv_rb_tv.setText(String.valueOf(item.getVoteAverage()));
            rb_tv.setRating(Float.parseFloat(String.valueOf(item.getVoteAverage())));

            Picasso.with(itemView.getContext())
                    .load(Config.IMAGE_W342+item.getPosterPath())
                    .fit()
                    .centerCrop()
                    .into(iv_poster_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}

