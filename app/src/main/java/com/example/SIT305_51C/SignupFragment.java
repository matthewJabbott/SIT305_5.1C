package com.example.SIT305_51C;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class SignupFragment extends Fragment {

    EditText etFullName, etUsername, etPassword, etConfirmPassword;
    Button btnCreateAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        etFullName = view.findViewById(R.id.etFullName);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        btnCreateAccount = view.findViewById(R.id.btnCreateAccount);

        btnCreateAccount.setOnClickListener(v -> {
            String name = etFullName.getText().toString().trim();
            String user = etUsername.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();
            String confirm = etConfirmPassword.getText().toString().trim();

            // 1. Basic validation before hitting the DB
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(confirm)) {
                Toast.makeText(getContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return;
            }

            // 2. Perform the Database Write on a Background Thread
            new Thread(() -> {
                // Use local instance to resolve the 'private' error
                AppDatabase db = AppDatabase.getInstance(requireContext());

                User newUser = new User(name, user, pass);
                db.userDao().registerUser(newUser);

                // 3. Return to the UI thread to navigate back to Login
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Account Created!", Toast.LENGTH_SHORT).show();
                        ((MainActivity)requireActivity()).replaceFragment(new LoginFragment());
                    });
                }
            }).start();
        });

        return view;
    }
}