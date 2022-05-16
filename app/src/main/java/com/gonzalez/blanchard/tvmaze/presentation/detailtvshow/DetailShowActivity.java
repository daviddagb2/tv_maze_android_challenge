package com.gonzalez.blanchard.tvmaze.presentation.detailtvshow;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import com.gonzalez.blanchard.tvmaze.adapters.rvEpisodeAdapter;
import com.gonzalez.blanchard.tvmaze.adapters.spSeasonsAdapter;
import com.gonzalez.blanchard.tvmaze.data.database.TvShowDatabaseRepository;
import com.gonzalez.blanchard.tvmaze.data.model.EpisodeModel;
import com.gonzalez.blanchard.tvmaze.data.model.SeasonModel;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import com.gonzalez.blanchard.tvmaze.data.repositories.TvShowRepository;
import com.gonzalez.blanchard.tvmaze.databinding.ActivityDetailShowBinding;
import com.gonzalez.blanchard.tvmaze.events.EpisodeRequestEvent;
import com.gonzalez.blanchard.tvmaze.events.SeasonRequestEvent;
import com.gonzalez.blanchard.tvmaze.presentation.detailepisode.DetailEpisodeActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

public class DetailShowActivity extends AppCompatActivity {

    private ActivityDetailShowBinding binding;

    private Toolbar toolbar;
    private CollapsingToolbarLayout toolBarLayout;
    private ImageView imagePoster;
    private TextView txtcontent, txttitle, txtgenres,txt_date,txt_seasons_count;
    private ProgressBar loadingSeason, loadingEpisodes;
    private Button btnWebsite;
    private AppCompatSpinner spinnerSeasonList;

    private TvShowModel tvShow;
    private List<SeasonModel> seasons = new ArrayList<SeasonModel>();

    TvShowRepository tvShowRepository;
    TvShowDatabaseRepository tvShowRepositoryDB;

    RecyclerView listEpisodes;
    private rvEpisodeAdapter adapter;
    private List<EpisodeModel> episodes = new ArrayList<>();

    private long currentSeason = 1;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("");

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        fab = binding.fab;
        fab.setOnClickListener(view -> addToFavorite());

        //Init tv show repository
        tvShowRepository = new TvShowRepository();
        tvShowRepositoryDB = new TvShowDatabaseRepository();

        imagePoster = binding.imagePoster;
        txtcontent = binding.contentscrolling.txtcontent;
        txttitle = binding.contentscrolling.txttitle;
        txtgenres = binding.contentscrolling.txtgenres;
        txt_date = binding.contentscrolling.txtDate;
        txt_seasons_count = binding.contentscrolling.txtSeasonsCount;
        loadingSeason = binding.contentscrolling.loadingSeason;
        loadingSeason.setVisibility(View.GONE);

        listEpisodes = binding.contentscrolling.listEpisodes;
        listEpisodes.setLayoutManager(new LinearLayoutManager(DetailShowActivity.this));
        listEpisodes.setHasFixedSize(true);

        spinnerSeasonList = binding.contentscrolling.spinnerSeasonList;

        loadingEpisodes = binding.contentscrolling.loadingEpisodes;
        loadingEpisodes.setVisibility(View.GONE);

        btnWebsite = binding.contentscrolling.btnWebsite;

        spinnerSeasonList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                currentSeason = seasons.get(position).getId();
                getEpisodes(currentSeason);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        initSpinnerSeasons();
        try{
            //get element detail
            tvShow = (TvShowModel) getIntent().getSerializableExtra("tvshowmodel");
            initData();
        }catch (Exception ex){
            Log.e("onCreate DE", ex.toString());
        }
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private  void initData(){
        Picasso.get().load(tvShow.getOriginalImageUrl()).into(imagePoster);
        txtcontent.setText( Html.fromHtml(tvShow.getSummary()) );
        toolBarLayout.setTitle(tvShow.getName());
        txttitle.setText(tvShow.getName());
        txtgenres.setText(tvShow.getGenres());
        txt_date.setText(tvShow.getYear());

        //Check if favorite element
        if(tvShow.getIsFavorite()){
            fab.setImageTintList(ColorStateList.valueOf(Color.rgb(255, 50, 50)));
        }

        //Depends of seasons
        txt_seasons_count.setText("");
        initSpinnerSeasons();
        getSeasons();
    }

    private void initSpinnerSeasons(){
        spSeasonsAdapter customAdapter = new spSeasonsAdapter(DetailShowActivity.this,seasons);
        spinnerSeasonList.setAdapter(customAdapter);
    }

    private void initEpisodeList(){
        try{
            //Crear el Adapter
            adapter = new rvEpisodeAdapter(episodes, new rvEpisodeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(EpisodeModel item) {
                    Intent mIntent = new Intent(DetailShowActivity.this, DetailEpisodeActivity.class);
                    Bundle mBundle = new Bundle();
                    mIntent.putExtra("episode", item);
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                }
            });
            listEpisodes.setAdapter(adapter);
        }catch (Exception ex){
            Log.e("initTvShowList", ex.toString());
        }
    }

    private void getSeasons(){
        try{
            loadingSeason.setVisibility(View.VISIBLE);
            tvShowRepository.getSeasons(tvShow.getId());
        }catch (Exception ex){
            Log.e("getTvShows", ex.toString());
            loadingSeason.setVisibility(View.GONE);
        }
    }

    private void getEpisodes(long seasonId){
        try{
            loadingEpisodes.setVisibility(View.VISIBLE);
            listEpisodes.setVisibility(View.GONE);
            tvShowRepository.getEpisodes(seasonId);
        }catch (Exception ex){
            Log.e("getTvShows", ex.toString());
            loadingEpisodes.setVisibility(View.GONE);
            listEpisodes.setVisibility(View.VISIBLE);
        }
    }

    private void addToFavorite(){
        if(tvShow.getIsFavorite() != true){
            tvShow.setIsFavorite(true);
            tvShowRepositoryDB.setFavoriteTvShow(tvShow);
            fab.setImageTintList(ColorStateList.valueOf(Color.rgb(255, 50, 50)));
            Snackbar.make(binding.fab, "TV Show added to favorites", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
            tvShow.setIsFavorite(false);
            tvShowRepositoryDB.removeFavoriteTvShow(tvShow);
            fab.setImageTintList(ColorStateList.valueOf(Color.rgb(0, 0, 0)));
            Snackbar.make(binding.fab, "TV Show removed from favorites", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SeasonRequestEvent event) {
        loadingSeason.setVisibility(View.GONE);
        if(event.success){
            this.seasons = event.getList();
            if(seasons.size() > 1){
                this.txt_seasons_count.setText(seasons.size() + " seasons");
            }
            initSpinnerSeasons();
        }else {
            Toast.makeText(DetailShowActivity.this, event.message, Toast.LENGTH_SHORT).show();
        }
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEpisode(EpisodeRequestEvent event) {
        loadingEpisodes.setVisibility(View.GONE);
        if(event.success){
            this.episodes = event.getList();
            if(episodes.size() > 0){
                listEpisodes.setVisibility(View.VISIBLE);
                initEpisodeList();
            }else{
                listEpisodes.setVisibility(View.GONE);
            }
        }else {
            Toast.makeText(DetailShowActivity.this, event.message, Toast.LENGTH_SHORT).show();
        }
    }




}