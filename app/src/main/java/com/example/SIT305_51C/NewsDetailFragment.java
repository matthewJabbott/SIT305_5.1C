package com.example.SIT305_51C;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsDetailFragment extends Fragment {

    private String title, desc, source;
    private int imgRes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve data passed from the Feed
        if (getArguments() != null) {
            title = getArguments().getString("title");
            desc = getArguments().getString("desc");
            source = getArguments().getString("source");
            imgRes = getArguments().getInt("img");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_detail, container, false);

        // 1. Populate UI with dynamic data
        ((TextView)v.findViewById(R.id.tvDetailTitle)).setText(title);
        ((TextView)v.findViewById(R.id.tvDetailDescription)).setText(desc);
        ((ImageView)v.findViewById(R.id.ivDetailImage)).setImageResource(imgRes);

        // 2. Bookmark Logic
        v.findViewById(R.id.btnBookmark).setOnClickListener(view -> {
            // 1. Prepare the data object
            BookmarkedNews bookmark = new BookmarkedNews(
                    MainActivity.loggedUserId, title, desc, source, imgRes
            );

            // 2. Run the database insert on a Background Thread
            new Thread(() -> {
                // Use local instance to avoid the "private access" error
                AppDatabase db = AppDatabase.getInstance(requireContext());
                db.bookmarkDao().bookmarkStory(bookmark);

                // 3. Return to the UI thread to notify the user
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Saved to Bookmarks", Toast.LENGTH_SHORT).show();
                    });
                }
            }).start();
        });

        // 3. Share Logic (The Common Intent requirement)
        v.findViewById(R.id.btnShare).setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
            intent.putExtra(Intent.EXTRA_TEXT, "Check out this story: " + title + "\n\n" + desc);
            startActivity(Intent.createChooser(intent, "Share via"));
        });

        return v;
    }
}