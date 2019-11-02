package com.dangerfield.kind.ui.feed;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dangerfield.kind.R;
import com.dangerfield.kind.api.Resource;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class FeedFragment extends Fragment {

    private FeedViewModel viewModel;
    private RecyclerView feedRecyclerView;
    private PostAdapter feedAdapter;
    private ImageView createPostButton;
    private CollapsingToolbarLayout collapsing_toolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_feed, container, false);
        collapsing_toolbar = v.findViewById(R.id.collapsing_toolbar);
        feedRecyclerView = v.findViewById(R.id.rv_feed);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpRecyclerView();

        collapsing_toolbar.setExpandedTitleTypeface(
                Typeface.create(collapsing_toolbar.getExpandedTitleTypeface(), Typeface.BOLD)
        );

        createPostButton = getActivity().findViewById(R.id.vst_center_image);
        ViewPager pager =  getActivity().findViewById(R.id.pager);

        createPostButton.setOnClickListener(view -> {
            if(pager.getCurrentItem() != 1){
                pager.setCurrentItem(1);
            }else{
                NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_createPostFragment);
            }
        });

        viewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        viewModel.getPostWithTag("dogs").observe(this, result -> {
            if(result instanceof Resource.Error){
                //show error
            }else if(result instanceof  Resource.Loading){
                //show loading

            }else if(result instanceof Resource.Success && result.getData() != null){
                feedAdapter.setPosts(result.getData());
            }
        });

    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        feedRecyclerView.setLayoutManager(layoutManager);
        feedAdapter = new PostAdapter(getActivity());
        feedRecyclerView.setAdapter(feedAdapter);
    }
}
