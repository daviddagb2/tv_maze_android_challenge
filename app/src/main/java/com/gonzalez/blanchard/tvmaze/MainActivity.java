package com.gonzalez.blanchard.tvmaze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.gonzalez.blanchard.tvmaze.config.config;
import com.gonzalez.blanchard.tvmaze.data.model.EpisodeModel;
import com.gonzalez.blanchard.tvmaze.presentation.detailepisode.DetailEpisodeActivity;
import com.gonzalez.blanchard.tvmaze.presentation.detailtvshow.DetailShowActivity;
import com.gonzalez.blanchard.tvmaze.presentation.search.SearchActivity;
import com.gonzalez.blanchard.tvmaze.presentation.security.SecurityActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.gonzalez.blanchard.tvmaze.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private boolean showLockScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showLockScreen = checkPinEnabled();
        Bundle b = getIntent().getExtras();
        if (getIntent().hasExtra("checkPin")) {
            int checkPin = b.getInt("checkPin");
            if(checkPin != 0){
                // Do something else
                Intent checkPinActivity = new Intent(MainActivity.this, SecurityActivity.class);
                startActivity(checkPinActivity);
                finish();
            }
        } else {
            if(showLockScreen == true){
                // Do something else
                Intent checkPinActivity = new Intent(MainActivity.this, SecurityActivity.class);
                startActivity(checkPinActivity);
                finish();
            }
        }






        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        binding.appBarMain.fab.setVisibility(View.INVISIBLE);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_favorite, R.id.nav_security_Pin, R.id.nav_about)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // Get the SearchView and set the searchable configuration
        MenuItem action_settings = (MenuItem) menu.findItem(R.id.action_settings);
        //findViewById(R.id.action_settings);

        action_settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //// Toast.makeText(MainActivity.this, "Buscar elemento", Toast.LENGTH_SHORT).show();

                Intent mIntent = new Intent(MainActivity.this, SearchActivity.class);
                Bundle mBundle = new Bundle();
                startActivity(mIntent);

                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean checkPinEnabled(){
        SharedPreferences sharedPref = MainActivity.this.getSharedPreferences(config.PREF_ROOT, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(config.PREF_PIN_ENABLED, false);
    }
}