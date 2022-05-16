package com.gonzalez.blanchard.tvmaze.presentation.detailepisode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.data.model.EpisodeModel;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import com.gonzalez.blanchard.tvmaze.databinding.ActivityDetailEpisodeBinding;
import com.gonzalez.blanchard.tvmaze.databinding.ActivityDetailShowBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

public class DetailEpisodeActivity extends AppCompatActivity {

    private ActivityDetailEpisodeBinding binding;

    ImageView imageEpisodeDetail;
    TextView txtEpisodeTitle, txtSeasonEpisode, textDetailEpisode;
    private Toolbar toolbar;
    private CollapsingToolbarLayout toolBarLayout;

    EpisodeModel currentEpisode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_episode);

        binding = ActivityDetailEpisodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("");

        imageEpisodeDetail = binding.imageEpisodeDetail;
        txtEpisodeTitle = binding.txtEpisodeTitle;
        txtSeasonEpisode = binding.txtSeasonEpisode;
        textDetailEpisode = binding.textDetailEpisode;

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        try{
            currentEpisode = (EpisodeModel) getIntent().getSerializableExtra("episode");
            initData();
        }catch (Exception ex){
            Log.e("onCreate DE", ex.toString());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void initData(){
        Picasso.get().load(currentEpisode.getOriginalImageUrl()).into(imageEpisodeDetail);
        txtEpisodeTitle.setText(currentEpisode.getName());
        txtSeasonEpisode.setText("Season " + currentEpisode.getSeason() + " - Episode " + currentEpisode.getNumber());
        textDetailEpisode.setText(Html.fromHtml(currentEpisode.getSummary()) );

    }

}