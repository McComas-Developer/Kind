package com.dangerfield.kind.ui.find;


import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dangerfield.kind.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class FindFragment extends Fragment {

    private CollapsingToolbarLayout collapsing_toolbar;
    private CardView searchButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_find, container, false);
        collapsing_toolbar = view.findViewById(R.id.collapsing_toolbar);
        searchButton = view.findViewById(R.id.search_button);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        collapsing_toolbar.setExpandedTitleTypeface(
                Typeface.create(collapsing_toolbar.getExpandedTitleTypeface(), Typeface.BOLD)
        );

        searchButton.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_searchFragment));
    }
}
