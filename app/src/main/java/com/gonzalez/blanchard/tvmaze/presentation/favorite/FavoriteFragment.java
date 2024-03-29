package com.gonzalez.blanchard.tvmaze.presentation.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.adapters.rvTvShowsAdapter;
import com.gonzalez.blanchard.tvmaze.data.database.TvShowDatabaseRepository;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import com.gonzalez.blanchard.tvmaze.data.repositories.TvShowRepository;
import com.gonzalez.blanchard.tvmaze.databinding.FragmentFavoriteBinding;
import com.gonzalez.blanchard.tvmaze.events.TvShowRequestEvent;
import com.gonzalez.blanchard.tvmaze.presentation.detailtvshow.DetailShowActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;

    private rvTvShowsAdapter adapter;
    RecyclerView list;
    View root;

    List<TvShowModel> listoftvshows = new ArrayList<>();
    ProgressBar loading;
    TvShowDatabaseRepository tvShowRepositoryDB;

    LinearLayout failedLoad;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoriteViewModel favoriteViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        failedLoad = binding.failedLoad;
        failedLoad.setVisibility(View.GONE);

        loading = root.findViewById(R.id.loading);
        list = root.findViewById(R.id.rvlist);
        list.setLayoutManager(new GridLayoutManager(root.getContext(),3));
        list.setHasFixedSize(true);

        //Init tv show repository
        tvShowRepositoryDB = new TvShowDatabaseRepository();

        //Init params on created
        onViewCreated();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
       // EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
       // EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        onViewCreated();
    }

    public void onViewCreated(){
        //getTvShows();
        getTvShows();

        //Init events list
        initTvShowList();
    }

    private void getTvShows(){
        try{
            loading.setVisibility(View.VISIBLE);
            this.listoftvshows = tvShowRepositoryDB.getFavoritesTvShowDB();
            loading.setVisibility(View.GONE);
        }catch (Exception ex){
            Log.e("getTvShows", ex.toString());
            loading.setVisibility(View.GONE);
        }
    }

    private void initTvShowList(){
        try{
            //Crear el Adapter
            adapter = new rvTvShowsAdapter(listoftvshows, new rvTvShowsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(TvShowModel item) {
                    ///Toast.makeText(root.getContext(), "item" + item.getId(), Toast.LENGTH_LONG).show();
                    Intent mIntent = new Intent(root.getContext(), DetailShowActivity.class);
                    Bundle mBundle = new Bundle();
                    mIntent.putExtra("tvshowmodel", item);
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                }
            });
            list.setAdapter(adapter);

        }catch (Exception ex){
            Log.e("initTvShowList", ex.toString());
        }
    }



}