package com.example.SIT305_51C;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BookmarksFragment extends Fragment {

    private NewsGridAdapter adapter;
    private final List<NewsItem> displayList = new ArrayList<>();
    private AppDatabase db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        db = AppDatabase.getInstance(getContext());

        RecyclerView rv = view.findViewById(R.id.rvBookmarks);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new NewsGridAdapter(displayList);
        rv.setAdapter(adapter);

        loadBookmarks();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView rv, @NonNull RecyclerView.ViewHolder vh, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    NewsItem itemToDelete = displayList.get(position);

                    new Thread(() -> {
                        db.bookmarkDao().deleteBookmark(itemToDelete.getId());

                        // 2. FIX: Added null-check for Activity to prevent NPE
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(() -> {
                                displayList.remove(position);
                                adapter.notifyItemRemoved(position);
                                Toast.makeText(getContext(), "Bookmark removed", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }).start();
                }
            }
        }).attachToRecyclerView(rv);

        view.findViewById(R.id.btnLogout).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).performLogout();
            }
        });

        return view;
    }

    private void loadBookmarks() {
        new Thread(() -> {
            List<BookmarkedNews> savedStories = db.bookmarkDao().getUserBookmarks(MainActivity.loggedUserId);
            List<NewsItem> tempList = new ArrayList<>();
            for (BookmarkedNews b : savedStories) {
                tempList.add(new NewsItem(b.id, b.title, b.description, b.source, b.imageResId, "Bookmarked"));
            }

            if (getActivity() != null && isAdded()) {
                getActivity().runOnUiThread(() -> {
                    displayList.clear();
                    displayList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                });
            }
        }).start();
    }
}