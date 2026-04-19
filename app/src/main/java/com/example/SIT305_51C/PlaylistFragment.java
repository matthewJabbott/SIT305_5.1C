package com.example.SIT305_51C;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends Fragment {

    private VideoAdapter adapter;
    private List<VideoItem> videoList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvPlaylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 1. Initialize with an empty list so the app doesn't crash while waiting
        adapter = new VideoAdapter(videoList);
        recyclerView.setAdapter(adapter);

        // 2. Load data asynchronously
        loadUserVideos();

        return view;
    }

    private void loadUserVideos() {
        new Thread(() -> {
            // Use local instance to avoid 'private access' error in MainActivity
            AppDatabase db = AppDatabase.getInstance(requireContext());

            // Perform the heavy lifting on this background thread
            List<VideoItem> userVideos = db.videoDao().getUserPlaylist(MainActivity.loggedUserId);

            // 3. Jump back to the UI thread to update the RecyclerView
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    videoList.clear();
                    videoList.addAll(userVideos);
                    adapter.notifyDataSetChanged();
                });
            }
        }).start();
    }
}