package com.dangerfield.kind.ui.find;


import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dangerfield.kind.R;
import com.dangerfield.kind.api.Resource;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class FindFragment extends Fragment {

    private CollapsingToolbarLayout collapsing_toolbar;
    private CardView searchButton;
    private RecyclerView findRecyclerView;
    private FindAdapter findAdapter;
    private FindViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_find, container, false);
        collapsing_toolbar = view.findViewById(R.id.collapsing_toolbar);
        searchButton = view.findViewById(R.id.search_button);
        findRecyclerView = view.findViewById(R.id.rv_popular_categories);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(FindViewModel.class);

        collapsing_toolbar.setExpandedTitleTypeface(
                Typeface.create(collapsing_toolbar.getExpandedTitleTypeface(), Typeface.BOLD)
        );

        searchButton.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_searchFragment));

        setUpRecyclerView();
        observePopularCategories();
    }

    private void observePopularCategories() {
        viewModel.getPopularCategories().observe(getViewLifecycleOwner(), categoriesResult -> {

            if (categoriesResult instanceof Resource.Success) {
                findAdapter.setCategories(categoriesResult.getData());
            }else if(categoriesResult instanceof Resource.Error){
                Toast.makeText(getContext(), categoriesResult.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        findRecyclerView.setLayoutManager(layoutManager);
        findAdapter = new FindAdapter(getActivity());
        findRecyclerView.setAdapter(findAdapter);
    }
}
