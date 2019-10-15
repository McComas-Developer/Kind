package com.dangerfield.kind.ui.profile;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dangerfield.kind.R;
import com.dangerfield.kind.api.CurrentUser;


public class ProfileFragment extends Fragment {

    private CurrentUser currentUser;
    private Button signOutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        currentUser = CurrentUser.INSTANCE;
        ((AppCompatActivity) getActivity()).setSupportActionBar(view.findViewById(R.id.profile_toolbar));
        if(currentUser.isAuthenticated()){ showProfile(view); }
        else{ showOnBoarding(view); }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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

    }

    private void setActionBarTitle(String s) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);
    }

    private void showProfile(View view) {
        setActionBarTitle(getString(R.string.string_profile_title));
        signOutButton = view.findViewById(R.id.btn_sign_out);
        view.findViewById(R.id.included_profile_view).setVisibility(View.VISIBLE);
        view.findViewById(R.id.included_authentication_view).setVisibility(View.GONE);

        signOutButton.setOnClickListener(it -> {
            CurrentUser.INSTANCE.signOut(getContext());
            reloadFragment();
        });
    }

    private void reloadFragment() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}
