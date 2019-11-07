package com.dangerfield.kind.ui.find;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.dangerfield.kind.R;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> {

    private Context context;
    private List<PopularCategory> categories = new ArrayList<>();

    public void setCategories(List<PopularCategory> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public FindAdapter(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTitle = itemView.findViewById(R.id.tv_popular_category);
            itemView.setOnClickListener(view -> {
                Toast.makeText(context,
                        categories.get(getAdapterPosition()).getItem() + "WAS CLICKED",
                        Toast.LENGTH_LONG).show();
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_popular_category,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTitle.setText((categories.get(position).getItem()));
    }

    @Override
    public int getItemCount(){ return categories.size(); }

}
