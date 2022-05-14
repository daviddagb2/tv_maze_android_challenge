package com.gonzalez.blanchard.tvmaze.presentation.detailtvshow;

import android.os.Bundle;

import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import com.gonzalez.blanchard.tvmaze.databinding.ActivityDetailShowBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailShowActivity extends AppCompatActivity {

    private ActivityDetailShowBinding binding;
    private ImageView imagePoster;
    private TextView txtcontent;
    private TvShowModel tvShow;
    private Toolbar toolbar;
    private CollapsingToolbarLayout toolBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("");

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        imagePoster = binding.imagePoster;
        txtcontent = binding.contentscrolling.txtcontent;

        try{
            //get element detail
            tvShow = (TvShowModel) getIntent().getSerializableExtra("tvshowmodel");
            initData();
        }catch (Exception ex){
            Log.e("onCreate DE", ex.toString());
        }
    }

    private  void initData(){
        Picasso.get().load(tvShow.getOriginalImageUrl()).into(imagePoster);
        txtcontent.setText(tvShow.getSummary());
        toolBarLayout.setTitle(tvShow.getName());
    }
}