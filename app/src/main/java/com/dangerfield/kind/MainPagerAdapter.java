package com.dangerfield.kind;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {



    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                return new FindFragment();

            case 1:
                return new FeedFragment();

            case 2:
                return new ProfileFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        //we will have 3 pages
        return 3;
    }

}