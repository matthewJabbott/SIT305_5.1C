package com.example.SIT305_51C;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private AppDatabase db;

    // Set this to 1 (or any valid ID in your DB) so the app has a "Global User"
    public static int loggedUserId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        bottomNav = findViewById(R.id.bottom_navigation);

        // 1. Start with the menu HIDDEN until they log in
        bottomNav.setVisibility(View.GONE);

        if (savedInstanceState == null) {
            // 2. Load the LoginFragment first
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_feed) {
                selectedFragment = new SportsFeedFragment();
            } else if (id == R.id.nav_bookmarks) {
                selectedFragment = new BookmarksFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

    public AppDatabase getDb() {
        return db;
    }
    public void updateBottomNavVisibility(boolean isVisible) {
        if (bottomNav != null) {
            bottomNav.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void performLogout() {
        loggedUserId = -1; // Accessing the static variable
        updateBottomNavVisibility(false);
        // Clear the backstack so user can't "back" into the app after logout
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();
    }
}