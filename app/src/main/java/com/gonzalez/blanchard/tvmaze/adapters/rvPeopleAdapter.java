package com.gonzalez.blanchard.tvmaze.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.data.model.EpisodeModel;
import com.gonzalez.blanchard.tvmaze.data.model.PersonModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class rvPeopleAdapter extends RecyclerView.Adapter<rvPeopleAdapter.PeopleViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(PersonModel item);
    }

    private final rvPeopleAdapter.OnItemClickListener listener;
    List<PersonModel> mData;

    public rvPeopleAdapter(List<PersonModel> mData, rvPeopleAdapter.OnItemClickListener listener) {
        this.listener = listener;
        this.mData = mData;
    }

    @Override
    public rvPeopleAdapter.PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new rvPeopleAdapter.PeopleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(rvPeopleAdapter.PeopleViewHolder holder, int position) {
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

    public static class PeopleViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePerson;
        TextView txtPersonName, txtPersonBirthday;

        public PeopleViewHolder(View itemView) {
            super(itemView);
            imagePerson = itemView.findViewById(R.id.imagePerson);
            txtPersonName = itemView.findViewById(R.id.txtPersonName);
            txtPersonBirthday = itemView.findViewById(R.id.txtPersonBirthday);
        }

        public void bind(final PersonModel item, final rvPeopleAdapter.OnItemClickListener listener) {
            txtPersonName.setText(item.getName());
            if(item.getBirthday() != null){
                txtPersonBirthday.setText(item.getBirthday());
                txtPersonBirthday.setVisibility(View.VISIBLE);
            }else{
                txtPersonBirthday.setVisibility(View.GONE);
            }
            if(item.getMediumImageUrl() != null){
                if(item.getMediumImageUrl().length() > 1){
                    Picasso.get().load(item.getMediumImageUrl()).into(imagePerson);
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
