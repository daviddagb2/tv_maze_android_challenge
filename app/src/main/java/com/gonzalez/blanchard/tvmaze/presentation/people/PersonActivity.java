package com.gonzalez.blanchard.tvmaze.presentation.people;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.gonzalez.blanchard.tvmaze.R;
import com.gonzalez.blanchard.tvmaze.adapters.rvTvShowsAdapter;
import com.gonzalez.blanchard.tvmaze.data.model.PersonModel;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import com.gonzalez.blanchard.tvmaze.data.repositories.PeopleRepository;
import com.gonzalez.blanchard.tvmaze.databinding.ActivityPersonBinding;
import com.gonzalez.blanchard.tvmaze.events.CastCreditRequestEvent;
import com.gonzalez.blanchard.tvmaze.presentation.detailtvshow.DetailShowActivity;
import com.gonzalez.blanchard.tvmaze.utils.CircleTransform;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

public class PersonActivity extends AppCompatActivity {

    private ActivityPersonBinding binding;

    ImageView imagePersonDetail;
    TextView txtName, txtBirthdayDetail, txtCountry;

    PersonModel person;
    private Toolbar toolbar;
    private CollapsingToolbarLayout toolBarLayout;
    private ProgressBar loadingShows;

    RecyclerView listTvShows;
    private rvTvShowsAdapter adapter;
    private List<TvShowModel> showcastlist = new ArrayList<TvShowModel>();

    PeopleRepository repository = new PeopleRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        binding = ActivityPersonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("");

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        imagePersonDetail = binding.imagePersonDetail;
        txtName = binding.txtName;
        txtBirthdayDetail = binding.txtBirthdayDetail;
        txtCountry = binding.txtCountry;
        loadingShows = binding.loadingShows;
        loadingShows.setVisibility(View.GONE);

        listTvShows = binding.listTvShows;
        listTvShows.setLayoutManager(new LinearLayoutManager(PersonActivity.this));
        listTvShows.setHasFixedSize(true);

        try{
            //get element detail
            person = (PersonModel) getIntent().getSerializableExtra("personmodel");
            initData();

            getTvShowCast();
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
        txtName.setText( person.getName());
        if(person.getBirthday() != null){
            txtBirthdayDetail.setText("Birthday: " + person.getBirthday());
            txtBirthdayDetail.setVisibility(View.VISIBLE);
        }else{
            txtBirthdayDetail.setVisibility(View.GONE);
        }

        if(person.getCountry() != null){
            txtCountry.setText("Country: " + person.getCountry());
            txtCountry.setVisibility(View.VISIBLE);
        }else{
            txtCountry.setVisibility(View.GONE);
        }

        toolBarLayout.setTitle(person.getName());

        if (person.getOriginalImageUrl().isEmpty()) {
            imagePersonDetail.setImageResource(R.drawable.ic_launcher_foreground);
        } else{
            Picasso.get().load(person.getOriginalImageUrl()).transform(new CircleTransform()).into(imagePersonDetail);
        }

        InitTvShowList();
    }

    private void InitTvShowList(){
        try{

            listTvShows.setLayoutManager(new GridLayoutManager(PersonActivity.this, 3));
            listTvShows.setHasFixedSize(true);
            loadingShows.setVisibility(View.GONE);
            //Crear el Adapter
            adapter = new rvTvShowsAdapter(showcastlist, new rvTvShowsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(TvShowModel item) {
                    Intent mIntent = new Intent(PersonActivity.this, DetailShowActivity.class);
                    Bundle mBundle = new Bundle();
                    mIntent.putExtra("tvshowmodel", item);
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                }
            });
            listTvShows.setAdapter(adapter);

        }catch (Exception ex){
            Log.e("initPersonList", ex.toString());
        }
    }

    private void getTvShowCast(){
        try{
            loadingShows.setVisibility(View.VISIBLE);
            repository.getPeopleCastCredits(person.getId());
        }catch (Exception ex){
            Log.e("getTvShows", ex.toString());
            loadingShows.setVisibility(View.GONE);
        }
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CastCreditRequestEvent event) {
        try{
            this.showcastlist = event.getList();
            initData();
        }catch (Exception ex){
            Log.e("initTvShowList", ex.toString());
        }
    }

}

