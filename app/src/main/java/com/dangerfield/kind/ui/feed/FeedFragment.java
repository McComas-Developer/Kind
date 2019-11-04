package com.dangerfield.kind.ui.feed;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.room.Room;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dangerfield.kind.R;
import com.dangerfield.kind.db.LikeIDDatabase;
import com.dangerfield.kind.model.LikeID;
import com.dangerfield.kind.model.Post;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    private ImageView createPostButton;
    private CollapsingToolbarLayout collapsing_toolbar;

    private RecyclerView rv;
    private FeedAdapter fa;
    private FeedViewModel fvm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_feed, container, false);
        collapsing_toolbar = v.findViewById(R.id.collapsing_toolbar);
        rv = v.findViewById(R.id.rv_feed);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        collapsing_toolbar.setExpandedTitleTypeface(
                Typeface.create(collapsing_toolbar.getExpandedTitleTypeface(), Typeface.BOLD)
        );

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        fa = new FeedAdapter(getContext(), new ArrayList<Post>());
        rv.setAdapter(fa);

        fvm = ViewModelProviders.of(this).get(FeedViewModel.class);
        fvm.getPosts().observe(getViewLifecycleOwner(), posts -> {
            fa.setPosts(posts);
        });

        createPostButton = getActivity().findViewById(R.id.vst_center_image);
        ViewPager pager =  getActivity().findViewById(R.id.pager);

        createPostButton.setOnClickListener(view -> {
            if(pager.getCurrentItem() != 1){
                pager.setCurrentItem(1);
            }else{
                NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_createPostFragment);
            }
        });

        LikeIDDatabase db = Room.databaseBuilder(getContext(),
                LikeIDDatabase.class, "likeIDTable").build();
        LikeID likeIDTable = new LikeID("");
        likeIDTable.setId("Whats up");

    }
}
