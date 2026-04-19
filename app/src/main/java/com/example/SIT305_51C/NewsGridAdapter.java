package com.example.SIT305_51C;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsGridAdapter extends RecyclerView.Adapter<NewsGridAdapter.NewsViewHolder> {

    private final List<NewsItem> newsList;
    private final List<NewsItem> newsListFull;

    public NewsGridAdapter(List<NewsItem> newsList) {
        this.newsList = newsList;
        this.newsListFull = new ArrayList<>(newsList);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_grid, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem item = newsList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvSource.setText(item.getSource());
        holder.ivImage.setImageResource(item.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (v.getContext() instanceof MainActivity) {
                NewsDetailFragment detailFragment = new NewsDetailFragment();

                // Pack the data for the DetailFragment
                Bundle args = new Bundle();
                args.putString("title", item.getTitle());
                args.putString("desc", item.getDesc());
                args.putString("source", item.getSource());
                args.putInt("img", item.getImageResId());
                detailFragment.setArguments(args);

                ((MainActivity) v.getContext()).replaceFragment(detailFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void filter(String category) {
        int oldSize = newsList.size();
        newsList.clear();
        notifyItemRangeRemoved(0, oldSize);

        if (category == null || category.isEmpty() || category.equals("All")) {
            newsList.addAll(newsListFull);
        } else {
            for (NewsItem item : newsListFull) {
                if (item.getCategory() != null && item.getCategory().equalsIgnoreCase(category)) {
                    newsList.add(item);
                }
            }
        }
        notifyItemRangeInserted(0, newsList.size());
    }

    // Inner class must be INSIDE the NewsGridAdapter class braces
    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivImage;
        final TextView tvSource, tvTitle;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivGridImage);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvTitle = itemView.findViewById(R.id.tvGridTitle);
        }
    }
}