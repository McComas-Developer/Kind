package com.dangerfield.kind.feed;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dangerfield.kind.R;

import java.util.ArrayList;
import java.util.Objects;

public class FeedFragment extends Fragment {

    private FeedViewModel viewModel;
    private RecyclerView feedRecyclerView;
    private FeedAdapter feedAdapter;


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
