package com.gonzalez.blanchard.tvmaze.presentation.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.adapters.rvPeopleAdapter;
import com.gonzalez.blanchard.tvmaze.adapters.rvTvShowsAdapter;
import com.gonzalez.blanchard.tvmaze.data.model.PersonModel;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import com.gonzalez.blanchard.tvmaze.data.repositories.PeopleRepository;
import com.gonzalez.blanchard.tvmaze.data.repositories.TvShowRepository;
import com.gonzalez.blanchard.tvmaze.databinding.ActivitySearchBinding;
import com.gonzalez.blanchard.tvmaze.events.PersonRequestEvent;
import com.gonzalez.blanchard.tvmaze.events.TvShowRequestEvent;
import com.gonzalez.blanchard.tvmaze.presentation.detailtvshow.DetailShowActivity;
import com.gonzalez.blanchard.tvmaze.presentation.people.PersonActivity;
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
    private rvPeopleAdapter adapterperson;
    RecyclerView list;
    List<TvShowModel> listoftvshows = new ArrayList<>();
    List<PersonModel> listofperson = new ArrayList<>();

    ProgressBar loading;
    EditText searchtext;
    TvShowRepository tvShowRepository = new TvShowRepository();
    PeopleRepository peopleRepository = new PeopleRepository();

    String searchBy = "TVSHOW"; // TVSHOW | PEOPLE

    RadioButton radiotvshow, radiopeople;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setup toolbar
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("Search TV Shows or People");

        //enable back button
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        loading = binding.progressBar;
        loading.setVisibility(View.GONE);
        list = binding.rvlista;
        list.setLayoutManager(new GridLayoutManager(SearchActivity.this,3));
        list.setHasFixedSize(true);
        searchtext = binding.searchtext;

        radiotvshow = binding.radiotvshow;
        radiopeople = binding.radiopeople;
        radiotvshow.setChecked(true);

        //Init events list
        initTvShowList();

        searchtext.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if( searchBy == "TVSHOW"){
                    getTvShows(s.toString());
                }else{
                    getPeople(s.toString());
                }
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

    private void getPeople(String search){
        try{
            loading.setVisibility(View.VISIBLE);
            peopleRepository.searchPeople(search);
        }catch (Exception ex){
            Log.e("getPeople", ex.toString());
            loading.setVisibility(View.GONE);
        }
    }

    private void initTvShowList(){
        try{
            list.setLayoutManager(new GridLayoutManager(SearchActivity.this,3));
            list.setHasFixedSize(true);

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


    private void initPersonList(){
        try{

            list.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
            list.setHasFixedSize(true);

            adapterperson = new rvPeopleAdapter(listofperson, new rvPeopleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(PersonModel item) {
                    Intent mIntent = new Intent(SearchActivity.this, PersonActivity.class);
                    Bundle mBundle = new Bundle();
                    mIntent.putExtra("personmodel", item);
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                }
            });

            list.setAdapter(adapterperson);
        }catch (Exception ex){
            Log.e("initPersonList", ex.toString());
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

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PersonRequestEvent event) {
        loading.setVisibility(View.GONE);
        if(event.success){
            this.listofperson = event.getList();
            initPersonList();
        }else {
            Toast.makeText(SearchActivity.this, event.message, Toast.LENGTH_SHORT).show();
        }
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radiotvshow:
                if (checked) {
                    searchBy = "TVSHOW";
                    }
                    break;
            case R.id.radiopeople:
                if (checked){
                    searchBy = "PEOPLE";
                }
                break;
        }
       /// Toast.makeText(SearchActivity.this, "on radio button" + searchBy, Toast.LENGTH_SHORT).show();
    }
}