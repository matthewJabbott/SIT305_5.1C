package com.example.SIT305_51C;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;

public class SportsFeedFragment extends Fragment {

    private NewsGridAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sports_feed, container, false);

        // 1. Data Initialization
        List<NewsItem> newsData = getDummyNews();

        // 2. Horizontal Top Stories
        RecyclerView rvTopStories = view.findViewById(R.id.rvTopStories);
        rvTopStories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // Using the same adapter logic for simplicity, or specific TopStoriesAdapter
        rvTopStories.setAdapter(new NewsGridAdapter(new ArrayList<>(newsData.subList(0, 2))));

        // 3. Vertical News Grid
        RecyclerView rvNewsGrid = view.findViewById(R.id.rvNewsGrid);
        rvNewsGrid.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvNewsGrid.setNestedScrollingEnabled(false);

        adapter = new NewsGridAdapter(newsData);
        rvNewsGrid.setAdapter(adapter);

        // 4. ChipGroup Listener for Filtering
        ChipGroup chipGroup = view.findViewById(R.id.chipGroup);
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                Chip selectedChip = group.findViewById(checkedIds.get(0));
                String category = selectedChip.getText().toString();
                // This calls the filter method we built in the Adapter
                adapter.filter(category);
            } else {
                // If user unselects, default back to showing all
                adapter.filter("All");
            }
        });

        return view;
    }

    private List<NewsItem> getDummyNews() {
        List<NewsItem> list = new ArrayList<>();
        int img = android.R.drawable.ic_menu_report_image;

        list.add(new NewsItem(1, "Victory at the MCG!", "A stunning goal...", "9NEWS", img, "Football"));
        list.add(new NewsItem(2, "Cricket Season Opens", "The pitch is green...", "7NEWS", img, "Cricket"));
        list.add(new NewsItem(3, "New Track Record", "The 100m sprint shattered...", "ABC NEWS", img, "Athletics"));
        list.add(new NewsItem(4, "Basketball Finals", "The team takes the lead...", "THE AGE", img, "Basketball"));

        return list;
    }
}