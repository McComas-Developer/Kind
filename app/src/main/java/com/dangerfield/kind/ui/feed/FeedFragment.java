package com.dangerfield.kind.ui.feed;

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
import android.widget.Button;
import android.widget.ImageView;

import com.dangerfield.kind.R;

import java.util.ArrayList;

public class FeedFragment extends Fragment {

    private FeedViewModel viewModel;
    private RecyclerView feedRecyclerView;
    private FeedAdapter feedAdapter;
    private ImageView createPostButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_feed, container, false);
        feedRecyclerView = v.findViewById(R.id.rv_feed);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpRecyclerView();

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

        viewModel.getPostWithTag("dogs").observe(this, updates -> {
            Log.d("posts","received " + updates.size() + " posts from firebase");
            feedAdapter.setPosts(updates);
        });

    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        feedRecyclerView.setLayoutManager(layoutManager);
        feedAdapter = new FeedAdapter(getActivity(), new ArrayList<>());
        feedRecyclerView.setAdapter(feedAdapter);
    }
}
