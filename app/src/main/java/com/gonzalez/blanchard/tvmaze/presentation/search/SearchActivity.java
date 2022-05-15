package com.gonzalez.blanchard.tvmaze.presentation.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.adapters.rvTvShowsAdapter;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import com.gonzalez.blanchard.tvmaze.data.repositories.TvShowRepository;
import com.gonzalez.blanchard.tvmaze.databinding.ActivityDetailShowBinding;
import com.gonzalez.blanchard.tvmaze.databinding.ActivitySearchBinding;
import com.gonzalez.blanchard.tvmaze.events.TvShowRequestEvent;
import com.gonzalez.blanchard.tvmaze.presentation.detailtvshow.DetailShowActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private Toolbar toolbar;
    private CollapsingToolbarLayout toolBarLayout;

    private rvTvShowsAdapter adapter;
    RecyclerView list;
    List<TvShowModel> listoftvshows = new ArrayList<>();
    ProgressBar loading;
    EditText searchtext;
    TvShowRepository tvShowRepository = new TvShowRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setup toolbar
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("");

        //enable back button
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        loading = binding.progressBar;
        list = binding.rvlista;
        list.setLayoutManager(new GridLayoutManager(SearchActivity.this,3));
        list.setHasFixedSize(true);

        searchtext = binding.searchtext;

        //Init events list
        initTvShowList();

        searchtext.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                // you can call or do what you want with your EditText here
                Toast.makeText(SearchActivity.this, "" + s, Toast.LENGTH_SHORT).show();
                // yourEditText...
                getTvShows(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void getTvShows(String search){
        try{
            loading.setVisibility(View.VISIBLE);
            tvShowRepository.searchTvShows(search);
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
                    Intent mIntent = new Intent(SearchActivity.this, DetailShowActivity.class);
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

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TvShowRequestEvent event) {
        loading.setVisibility(View.GONE);
        if(event.success){
            this.listoftvshows = event.getList();
            initTvShowList();
        }else {
            Toast.makeText(SearchActivity.this, event.message, Toast.LENGTH_SHORT).show();
        }
    }



}