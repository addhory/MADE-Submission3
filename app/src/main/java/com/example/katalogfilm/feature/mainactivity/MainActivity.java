package com.example.katalogfilm.feature.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.katalogfilm.R;
import com.example.katalogfilm.base.BaseAppCompatActivity;
import com.example.katalogfilm.feature.favoritefrag.FavoriteMovFragment;
import com.example.katalogfilm.feature.nowplayingfrag.NowPlayingFragment;
import com.example.katalogfilm.feature.upcomingfrag.UpcomingFragment;

public class MainActivity extends BaseAppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Fragment pageContent = new NowPlayingFragment();

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //set load NowFrag
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, pageContent)
                    .commit();
        }else{
        pageContent= getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,pageContent);
        }


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, pageContent);
        super.onSaveInstanceState(outState);
    }



   /* private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nowPlaying:
                pageContent = new NowPlayingFragment();
                break;
            case R.id.upComing:
                pageContent = new UpcomingFragment();
                break;
            case R.id.favoriteMovie:
                pageContent=new FavoriteMovFragment();
                break;

        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, pageContent)
                .commit();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
