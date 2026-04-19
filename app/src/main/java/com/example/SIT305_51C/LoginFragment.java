package com.example.SIT305_51C;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    private EditText etUser, etPass;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUser = view.findViewById(R.id.etLoginUsername);
        etPass = view.findViewById(R.id.etLoginPassword);
        Button btnLogin = view.findViewById(R.id.btnLogin);
        Button btnToSignup = view.findViewById(R.id.btnToSignup);

        btnLogin.setOnClickListener(v -> {
            String u = etUser.getText().toString().trim();
            String p = etPass.getText().toString().trim();

            if (u.isEmpty() || p.isEmpty()) {
                Toast.makeText(getContext(), "Please enter all details", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1. Get local DB instance and run query on Background Thread
            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(requireContext());
                User authenticatedUser = db.userDao().login(u, p);

                // 2. Return to UI Thread to handle the result
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (authenticatedUser != null) {
                            // Set the session ID
                            MainActivity.loggedUserId = authenticatedUser.id;

                            // Navigate to Home
                            ((MainActivity) requireActivity()).replaceFragment(new HomeFragment());

                            // Show the bottom nav now that we are logged in
                            ((MainActivity) requireActivity()).updateBottomNavVisibility(true);
                        } else {
                            Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        });

        btnToSignup.setOnClickListener(v ->
                ((MainActivity) requireActivity()).replaceFragment(new SignupFragment()));

        return view;
    }
}