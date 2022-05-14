package com.gonzalez.blanchard.tvmaze.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.data.models.TvShow;
import com.gonzalez.blanchard.tvmaze.presentation.detailtvshow.DetailTvShowActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class rvTvShowsAdapter extends RecyclerView.Adapter<rvTvShowsAdapter.TvShowViewHolder> {

    Context ctx;
    final LayoutInflater mLayoutInflater;
    List<TvShow> mData;

    public rvTvShowsAdapter(Context context, List<TvShow> mData) {
        this.ctx = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @Override
    public TvShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_tv_show, parent, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TvShowViewHolder holder,
                                 int position) {
        try{
            holder.itemView.setOnClickListener(v -> {
                try{
                    Intent mIntent = new Intent(ctx, DetailTvShowActivity.class);
                    Bundle mBundle = new Bundle();
                    mIntent.putExtra("tvshow", mData.get(position)); // enviamos el objeto serializado
                    mIntent.putExtras(mBundle);
                    ctx.startActivity(mIntent);

                }catch (Exception ex){

                }
            });

            Picasso.get().load(mData.get(position).getImage().getMedium()).into(holder.imageTvShow);


        }catch (Exception ex){
            Log.e("onBindViewHolder", ex.toString());
        }
    }

    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.size();
        }else{
            return 0;
        }
    }

    public static class TvShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imageTvShow;
        TextView txtname, txtdate, txtaverage;
        public TvShowViewHolder(View itemView) {
            super(itemView);
            imageTvShow = itemView.findViewById(R.id.imageshow);
            txtname = itemView.findViewById(R.id.txtname);
            txtdate = itemView.findViewById(R.id.txtdate);
            txtaverage = itemView.findViewById(R.id.txtaverage);
        }
    }
}
