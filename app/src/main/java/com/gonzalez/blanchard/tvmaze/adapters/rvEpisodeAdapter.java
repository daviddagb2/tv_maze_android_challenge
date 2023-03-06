package com.gonzalez.blanchard.tvmaze.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.data.model.EpisodeModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class rvEpisodeAdapter extends RecyclerView.Adapter<rvEpisodeAdapter.EpisodeViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(EpisodeModel item);
    }

    private final rvEpisodeAdapter.OnItemClickListener listener;
    List<EpisodeModel> mData;

    public rvEpisodeAdapter(List<EpisodeModel> mData, rvEpisodeAdapter.OnItemClickListener listener) {
        this.listener = listener;
        this.mData = mData;
    }

    @Override
    public rvEpisodeAdapter.EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode, parent, false);
        return new rvEpisodeAdapter.EpisodeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(rvEpisodeAdapter.EpisodeViewHolder holder, int position) {
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

    public static class EpisodeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageEpisode;
        TextView txtEpisodeName, txtTime, txtSummary;

        public EpisodeViewHolder(View itemView) {
            super(itemView);
            imageEpisode = itemView.findViewById(R.id.imageEpisode);
            txtEpisodeName = itemView.findViewById(R.id.txtEpisodeName);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtSummary = itemView.findViewById(R.id.txtSummary);
        }

        public void bind(final EpisodeModel item, final rvEpisodeAdapter.OnItemClickListener listener) {
            txtEpisodeName.setText("Episode " + item.getNumber() + " - " + item.getName());
            txtTime.setVisibility(View.GONE);
            if(item.getMediumImageUrl() != null){
                if(item.getMediumImageUrl().length() > 1){
                    if (item.getMediumImageUrl().isEmpty()) {
                        imageEpisode.setImageResource(R.drawable.ic_launcher_foreground);
                    } else{
                        Picasso.get().load(item.getMediumImageUrl()).into(imageEpisode);
                    }
                }
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
