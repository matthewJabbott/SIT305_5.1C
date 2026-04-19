package com.example.SIT305_51C;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private EditText etUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        etUrl = v.findViewById(R.id.etVideoUrl);

        v.findViewById(R.id.btnPlay).setOnClickListener(view -> {
            String url = etUrl.getText().toString();
            if (!url.isEmpty()) {
                navigateToPlayer(url);
            }
        });

        v.findViewById(R.id.btnAddToPlaylist).setOnClickListener(view -> {
            String url = etUrl.getText().toString();
            if(!url.isEmpty()) {
                // 1. Create the data object
                VideoItem item = new VideoItem(MainActivity.loggedUserId, url, "Saved Video");

                // 2. Perform DB operation on a Background Thread
                new Thread(() -> {
                    // Get local instance instead of accessing private MainActivity.db
                    AppDatabase db = AppDatabase.getInstance(requireContext());
                    db.videoDao().addVideo(item);

                    // 3. Return to UI thread to show confirmation
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(), "Added to Playlist!", Toast.LENGTH_SHORT).show();
                            etUrl.setText(""); // Clear the input
                        });
                    }
                }).start();
            }
        });

        // 3. Navigation to Playlist
        v.findViewById(R.id.btnMyPlaylist).setOnClickListener(view ->
                ((MainActivity)requireActivity()).replaceFragment(new PlaylistFragment()));

        // 4. Logout Logic
        v.findViewById(R.id.btnLogout).setOnClickListener(view -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).performLogout();
            }
        });
        return v;
    }

    private void navigateToPlayer(String url) {
        // 1. Create the fragment instance
        PlayerFragment playerFrag = new PlayerFragment();

        // 2. Create a Bundle to pass the URL "payload"
        Bundle args = new Bundle();
        args.putString("video_url", url);
        playerFrag.setArguments(args);

        // 3. Perform the fragment transaction via MainActivity
        ((MainActivity)requireActivity()).replaceFragment(playerFrag);
    }
}