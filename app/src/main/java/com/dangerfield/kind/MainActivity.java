package com.dangerfield.kind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dangerfield.kind.api.Repository;
import com.dangerfield.kind.model.Post;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewPager();
    }

    private void setupViewPager() {
        ViewPager viewPager =  findViewById(R.id.pager);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1); //so it starts off at feed
        viewPager.setOffscreenPageLimit(2); //lets fragments remain in memory

        TabViews snapTabsView =  findViewById(R.id.am_snap_tabs);
        snapTabsView.initViewPager(viewPager);
    }
}

