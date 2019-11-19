package com.dangerfield.kind.ui.profile;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dangerfield.kind.R;
import java.util.ArrayList;


public class PostsFragment extends Fragment {
    private MyRecyclerViewAdapter recycle;
    public ArrayList<String> Desserts = new ArrayList<>();
    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Desserts.add("Cake");
        Desserts.add("IceCream");
        Desserts.add("Chocolate");
        Desserts.add("Candy");
        Desserts.add("Marshmellow");
        Desserts.add("Vanilla");
        Desserts.add("Birthday Cake");
        Desserts.add("Pumpkin Pie");
        Desserts.add("Cherry Pie");
        Desserts.add("Peach Cobbler");
        View vw = inflater.inflate(R.layout.fragment_posts, container, false);
        //Set Up Recycler view
//        RecyclerView feedRecyclerView;
//        feedRecyclerView = vw.findViewById(R.id.view_profile_posts);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        feedRecyclerView.setLayoutManager(layoutManager);

        recycle = new MyRecyclerViewAdapter(getContext(), Desserts);
        return vw;
    }
}
