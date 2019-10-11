package com.dangerfield.kind;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.dangerfield.kind.ui.feed.FeedFragment;
import com.dangerfield.kind.ui.find.FindFragment;
import com.dangerfield.kind.ui.profile.ProfileFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
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