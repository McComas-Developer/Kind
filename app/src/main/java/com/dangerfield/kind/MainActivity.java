package com.dangerfield.kind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

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

