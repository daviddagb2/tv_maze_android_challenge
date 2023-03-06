package com.gonzalez.blanchard.tvmaze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.data.model.SeasonModel;
import java.util.List;

public class spSeasonsAdapter extends BaseAdapter {

    Context context;
    List<SeasonModel> listseasons;
    LayoutInflater inflater;

    public spSeasonsAdapter(Context applicationContext, List<SeasonModel> listseasons) {
        this.context = applicationContext;
        this.listseasons = listseasons;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return listseasons.size();
    }

    @Override
    public Object getItem(int i) {
        return listseasons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listseasons.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        String name = "Season " + listseasons.get(i).getNumber();
        if(listseasons.get(i).getName() != null){
           // name = name + " - " + listseasons.get(i).getName();
        }
        names.setText(name);
        return view;
    }

}
