package com.dangerfield.kind.ui.feed;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.room.Room;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.dangerfield.kind.R;
import com.dangerfield.kind.db.LikeDAO;
import com.dangerfield.kind.db.LikeIDDatabase;
import com.dangerfield.kind.model.LikeID;
import com.dangerfield.kind.model.Post;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import com.dangerfield.kind.api.CurrentUser;
import com.dangerfield.kind.api.Resource;
import com.dangerfield.kind.model.Post;
import com.dangerfield.kind.util.AlertFactory;
import com.dangerfield.kind.util.ExtensionsKt;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

public class FeedFragment extends Fragment {

    private FeedViewModel viewModel;
    private RecyclerView feedRecyclerView;
    private PostAdapter feedAdapter;
    private ImageView createPostButton;
    private ProgressBar pb_feed;
    private CollapsingToolbarLayout collapsing_toolbar;
    private SwipeRefreshLayout swipe_refresh_layout;
    private LikeIDDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_feed, container, false);
        collapsing_toolbar = v.findViewById(R.id.collapsing_toolbar);
        feedRecyclerView = v.findViewById(R.id.rv_feed);
        swipe_refresh_layout = v.findViewById(R.id.swipe_refresh_layout);
        pb_feed = v.findViewById(R.id.pb_feed);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        viewModel.setCurrentUser(CurrentUser.INSTANCE);

        setUpRecyclerView();
        setupRefresher();
        setupPostRequest();
        observeFeed();

        collapsing_toolbar.setExpandedTitleTypeface(
                Typeface.create(collapsing_toolbar.getExpandedTitleTypeface(), Typeface.BOLD)
        );
    }


    private void observeFeed() {
        viewModel.getFeed().observe(this, result -> {

            ExtensionsKt.showIFF(pb_feed, result instanceof Resource.Loading
                    && !((Resource.Loading<List<Post>>) result).getRefreshing());

            if(result instanceof Resource.Error)
                Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_LONG).show();

            else if(result.getData() != null)
                feedAdapter.setPosts(result.getData());

            if (result.getData() != null
                    && result.getData().size() > 0) swipe_refresh_layout.setRefreshing(false);

        });
    }

    private void setupPostRequest() {
        createPostButton = getActivity().findViewById(R.id.vst_center_image);
        ViewPager pager = getActivity().findViewById(R.id.pager);

        createPostButton.setOnClickListener(view -> {
            if (pager.getCurrentItem() != 1) {
                pager.setCurrentItem(1);
            } else {
                if (CurrentUser.INSTANCE.isAuthenticated()) {
                    NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_createPostFragment);
                } else {
                    pager.setCurrentItem(2);
                }

            }
        });
    }

    private void setupRefresher() {
        swipe_refresh_layout.setColorSchemeResources( R.color.colorPrimary, android.R.color.holo_blue_light
                , android.R.color.holo_blue_dark);

        swipe_refresh_layout.setOnRefreshListener(() -> {
            viewModel.refreshFeed();
        });
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        feedRecyclerView.setLayoutManager(layoutManager);
        feedAdapter = new PostAdapter(getActivity(), viewModel);
        feedRecyclerView.setAdapter(feedAdapter);
    }
}
