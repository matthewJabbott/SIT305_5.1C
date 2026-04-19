package com.example.SIT305_51C;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<VideoItem> videoList;

    public VideoAdapter(List<VideoItem> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout for each row
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoItem item = videoList.get(position);
        holder.tvUrl.setText(item.videoUrl);

        // 2. Handle Navigation to PlayerFragment
        holder.itemView.setOnClickListener(v -> {
            PlayerFragment playerFrag = new PlayerFragment();
            Bundle args = new Bundle();
            args.putString("video_url", item.videoUrl);
            playerFrag.setArguments(args);

            // Access the activity through the context and trigger fragment swap
            if (v.getContext() instanceof MainActivity) {
                ((MainActivity) v.getContext()).replaceFragment(playerFrag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList != null ? videoList.size() : 0;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView tvUrl;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUrl = itemView.findViewById(R.id.tvVideoUrl);
        }
    }
}