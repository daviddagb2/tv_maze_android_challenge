package com.gonzalez.blanchard.tvmaze.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class rvTvShowsAdapter extends RecyclerView.Adapter<rvTvShowsAdapter.TvShowViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(TvShowModel item);
    }

    private final OnItemClickListener listener;
    List<TvShowModel> mData;

    public rvTvShowsAdapter(List<TvShowModel> mData, OnItemClickListener listener) {
        this.listener = listener;
        this.mData = mData;
    }

    @Override
    public TvShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_show, parent, false);
        return new TvShowViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TvShowViewHolder holder, int position) {
        holder.bind(mData.get(position), listener);
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
        ImageView imageTvShow, imageHeart;
        TextView txtname, txtdate, txtaverage;
        public TvShowViewHolder(View itemView) {
            super(itemView);
            imageTvShow = itemView.findViewById(R.id.imageshow);
            txtname = itemView.findViewById(R.id.txtname);
            txtdate = itemView.findViewById(R.id.txtdate);
            txtaverage = itemView.findViewById(R.id.txtaverage);
            imageHeart = itemView.findViewById(R.id.imageHeart);
        }

        public void bind(final TvShowModel item, final OnItemClickListener listener) {
            txtname.setText(item.getName());
            txtdate.setText(item.getYear());
            txtaverage.setText("" + item.getGenres());
            if (item.getMediumImageUrl().isEmpty()) {
                imageTvShow.setImageResource(R.drawable.ic_launcher_foreground);
            } else{
                Picasso.get().load(item.getMediumImageUrl()).into(imageTvShow);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
            if(item.getIsFavorite() == true){
                imageHeart.setVisibility(View.VISIBLE);
            }
        }
    }
}
