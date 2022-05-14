package com.gonzalez.blanchard.tvmaze.presentation.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.adapters.rvTvShowsAdapter;
import com.gonzalez.blanchard.tvmaze.data.models.Image;
import com.gonzalez.blanchard.tvmaze.data.models.TvShow;
import com.gonzalez.blanchard.tvmaze.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private rvTvShowsAdapter adapter;
    RecyclerView list;
    HomeViewModel homeViewModel;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        list = root.findViewById(R.id.rvlist);
        list.setLayoutManager(new GridLayoutManager(root.getContext(),2));
        list.setHasFixedSize(true);
        //list.setLayoutManager(new LinearLayoutManager(root.getContext()));

        //Init params on created
        onViewCreated();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onViewCreated(){

        //getTvShows();

        //Init events list
        initTvShowList();
    }

    private void getTvShows(){
        try{


        }catch (Exception ex){
            Log.e("getTvShows", ex.toString());
        }
    }

    private void initTvShowList(){
        try{
            List<TvShow> listalocal = new ArrayList<TvShow>();
            for(int i = 0; i<=30; i++){
                TvShow tvshow = new TvShow();
                tvshow.setId(i);
                tvshow.setUrl("https://www.tvmaze.com/shows/1/under-the-dome");
                tvshow.setName("Under the Dome");
                tvshow.setType("Scripted");
                tvshow.setLanguage("English");
                tvshow.setStatus("Ended");
                tvshow.setRuntime(60);
                tvshow.setAverageRuntime(60);
                tvshow.setPremiered("2013-06-24");
                tvshow.setEnded("2015-09-10");
                tvshow.setOfficialSite("http://www.cbs.com/shows/under-the-dome/");

                Image titleImage = new Image();
                titleImage.setMedium("https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg");
                titleImage.setOriginal("https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg");

                tvshow.setImage(titleImage);
                listalocal.add(tvshow);
            }

            //Crear el Adapter
            adapter = new rvTvShowsAdapter(root.getContext(), listalocal);
            list.setAdapter(adapter);

            /*if(emergencieslist != null){
                if(emergencieslist.size() > 0){
                    rlempty.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                }else {
                    rlempty.setVisibility(View.VISIBLE);
                    list.setVisibility(View.GONE);
                }
            }else{
                rlempty.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
            }*/

        }catch (Exception ex){
            Log.e("initTvShowList", ex.toString());
        }
    }


}