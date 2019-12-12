package com.idw.project.cataloguemovie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.idw.project.cataloguemovie.CustomOnItemClickListener;
import com.idw.project.cataloguemovie.R;
import com.idw.project.cataloguemovie.activity.DetailTvShowActivity;
import com.idw.project.cataloguemovie.config.Config;
import com.idw.project.cataloguemovie.model.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.CONTENT_URI_TVSHOW;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteTvShowViewholder> {
    private Activity activity;

    private ArrayList<TvShow> dataList = new ArrayList<>();

    public FavoriteTvShowAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<TvShow> getListTvShow() {
        return dataList;
    }

    public void setListTvShow(ArrayList<TvShow> listTvShow) {

        if (listTvShow.size() > 0) {
            this.dataList.clear();
        }
        this.dataList.addAll(listTvShow);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteTvShowViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tvshow_item, parent,
                false);

        return new FavoriteTvShowViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvShowViewholder favoriteTvShowViewholder, int position) {
        favoriteTvShowViewholder.tv_title_tv.setText(getListTvShow().get(position).getOriginalName());
        favoriteTvShowViewholder.tv_release_date_tv.setText(getListTvShow().get(position).getFirstAirDate());
        favoriteTvShowViewholder.tv_description_tv.setText(getListTvShow().get(position).getOverview());
        favoriteTvShowViewholder.tv_rb_tv.setText(String.valueOf(getListTvShow().get(position).getVoteAverage()));
        favoriteTvShowViewholder.rb_tv.setRating(Float.parseFloat(String.valueOf(getListTvShow().get(position).getVoteAverage())));

        Picasso.with(activity)
                .load(Config.IMAGE_W342+getListTvShow().get(position).getPosterPath())
                .fit()
                .centerCrop()
                .into(favoriteTvShowViewholder.iv_poster_tv);

        favoriteTvShowViewholder.cv_tv.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailTvShowActivity.class);

                Uri uri = Uri.parse(CONTENT_URI_TVSHOW + "/" + getListTvShow().get(position).getId());

                intent.setData(uri);
                intent.putExtra(DetailTvShowActivity.TAG, dataList.get(position));
                activity.startActivity(intent);
            }
        }));

    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() :0;
    }

    class FavoriteTvShowViewholder extends RecyclerView.ViewHolder{
        ImageView iv_poster_tv;
        TextView tv_title_tv, tv_release_date_tv, tv_description_tv, tv_rb_tv;
        CardView cv_tv;
        RatingBar rb_tv;

        FavoriteTvShowViewholder(@NonNull View itemView) {
            super(itemView);
            iv_poster_tv = itemView.findViewById(R.id.iv_poster_tv);
            tv_title_tv = itemView.findViewById(R.id.tv_title_tv);
            tv_release_date_tv = itemView.findViewById(R.id.tv_release_date_tv);
            rb_tv = itemView.findViewById(R.id.rb_tv);
            tv_rb_tv = itemView.findViewById(R.id.tv_rb_tv);
            tv_description_tv = itemView.findViewById(R.id.tv_description_tv);
            cv_tv = itemView.findViewById(R.id.cv_tv);
        }

    }

}
