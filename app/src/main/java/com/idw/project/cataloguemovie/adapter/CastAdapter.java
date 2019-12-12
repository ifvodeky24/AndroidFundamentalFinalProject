package com.idw.project.cataloguemovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.idw.project.cataloguemovie.R;
import com.idw.project.cataloguemovie.model.Cast;

import java.util.ArrayList;

import static com.idw.project.cataloguemovie.config.Config.IMAGE_W342;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Cast> casts;

    public CastAdapter(Context context, ArrayList<Cast> casts){
        this.context = context;
        this.casts = casts;
    }

    @NonNull
    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cast, viewGroup, false);
        return new CastAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.ViewHolder viewHolder, int position) {
        viewHolder.tv_cast_name.setText(casts.get(position).getName());

        Glide.with(context)
                .load(IMAGE_W342+casts.get(position).getProfilePath())
                .into(viewHolder.iv_cast);

    }

    @Override
    public int getItemCount() {
        return (casts != null) ? casts.size() :0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_cast;
        TextView tv_cast_name;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_cast = itemView.findViewById(R.id.iv_cast);
            tv_cast_name = itemView.findViewById(R.id.tv_cast_name);
        }
    }
}

