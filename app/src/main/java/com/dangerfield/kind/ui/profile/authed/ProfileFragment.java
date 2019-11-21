package com.dangerfield.kind.ui.profile.authed;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.dangerfield.kind.R;
import com.dangerfield.kind.api.CurrentUser;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;


public class ProfileFragment extends Fragment {

    private CurrentUser currentUser;
    private ImageButton settingsButton;
    private CollapsingToolbarLayout collapsing_toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        currentUser = CurrentUser.INSTANCE;
        ((AppCompatActivity) getActivity()).setSupportActionBar(view.findViewById(R.id.profile_toolbar));
        if(currentUser.isAuthenticated()){ showProfile(view); }
        else{
            collapsing_toolbar = view.findViewById(R.id.profile_collapsing_toolbar);
            showOnBoarding(view);
        }
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setActionBarTitle("");
    }

    private void showOnBoarding(View view) {

        setActionBarTitle("");
        view.findViewById(R.id.btn_sign_up).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_signUpFragment);
        });

        view.findViewById(R.id.btn_log_in).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_loginFragment);
        });

        view.findViewById(R.id.included_authentication_view).setVisibility(View.VISIBLE);
        view.findViewById(R.id.included_profile_view).setVisibility(View.GONE);
        view.findViewById(R.id.profile_settings).setVisibility(View.GONE);
        view.findViewById(R.id.profile_user).setVisibility(View.GONE);

        AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) collapsing_toolbar.getLayoutParams();
        p.setScrollFlags(0);
        collapsing_toolbar.setLayoutParams(p);
    }

    private void setActionBarTitle(String s) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);
    }

    private void showProfile(View view) {
        settingsButton = view.findViewById(R.id.profile_settings);

        ViewPager pager = view.findViewById(R.id.viewPager);
        pager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
        TabLayout tabLayout= view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        view.findViewById(R.id.included_profile_view).setVisibility(View.VISIBLE);
        view.findViewById(R.id.included_authentication_view).setVisibility(View.GONE);

        settingsButton.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_settingsFragment);
        });
    }
}
