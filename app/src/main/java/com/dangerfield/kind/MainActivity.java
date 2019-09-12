package com.dangerfield.kind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1); //so it starts off at feed

        //this method should retain the instance of the other fragments so that they arent recreated every time
        viewPager.setOffscreenPageLimit(2);

        TabViews snapTabsView = (TabViews) findViewById(R.id.am_snap_tabs);
        snapTabsView.initViewPager(viewPager);

    }
}

