package com.idw.project.cataloguemovie.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.idw.project.cataloguemovie.R;
import com.idw.project.cataloguemovie.fragment.FavoriteFragment;
import com.idw.project.cataloguemovie.fragment.MovieFragment;
import com.idw.project.cataloguemovie.fragment.SettingsFragment;
import com.idw.project.cataloguemovie.fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    MenuItem navigation_movie, navigation_tvshow, navigation_favorite, navigation_setting;
    Menu menu_bottom;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fungsi untuk Bottom Navigation
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        menu_bottom = navigation.getMenu();

        navigation_movie = menu_bottom.findItem(R.id.navigation_movie);
        navigation_tvshow = menu_bottom.findItem(R.id.navigation_tvshow);
        navigation_favorite  = menu_bottom.findItem(R.id.navigation_favorite);
        navigation_setting = menu_bottom.findItem(R.id.navigation_setting);

        if (savedInstanceState == null){
            fragment = new MovieFragment();
            loadFragment(fragment);
        }else {
            Log.d(String.valueOf(getApplicationContext()), "cek");
        }

    }

    //Fungsi untuk Bottom Navigation saat di klik
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()){
                case R.id.navigation_movie:
                    fragment = new MovieFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_tvshow:
                    fragment = new TvShowFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_favorite:
                    fragment = new FavoriteFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_setting:
                    fragment = new SettingsFragment();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.btt, R.anim.fade_out);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

}
