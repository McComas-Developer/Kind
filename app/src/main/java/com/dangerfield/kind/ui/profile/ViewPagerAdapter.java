package com.dangerfield.kind.ui.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String[] titles = {"Likes", "Your Posts"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new LikesFragment();

            case 1:
                return new PostsFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        //we will have 2 pages
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
