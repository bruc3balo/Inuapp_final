package com.example.inuapp.login.loginPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.inuapp.MainActivity;
import com.example.inuapp.R;
import com.example.inuapp.admin.AdminActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.inuapp.models.User.ROLE;
import static com.example.inuapp.models.User.ROLE_ADMIN;
import static com.example.inuapp.models.User.ROLE_CUSTOMER;
import static com.example.inuapp.models.User.USERS;
import static com.example.inuapp.models.User.USER_DETAILS;


public class LoginPage extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.login_page_fragment, container, false);

        EditText emailL = v.findViewById(R.id.emailL);
        EditText password = v.findViewById(R.id.passwordL);
        Button loginB = v.findViewById(R.id.login_button);

        loginB.setOnClickListener(v1 -> {
            if (validForm(emailL, password)) {
                loginUserWithFirebase(emailL.getText().toString(), password.getText().toString(), loginB);
                loginB.setEnabled(false);
            } else {
                Toast.makeText(requireContext(), "Check details", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private boolean validForm(EditText email, EditText pass) {
        boolean valid = false;
        if (!email.getText().toString().contains("@")) {
            email.setError("Wrong email format");
            email.requestFocus();
        } else if (pass.getText().toString().length() < 6) {
            pass.setError("Password too short");
            pass.requestFocus();
        } else {
            valid = true;
        }
        return valid;
    }

    private void loginUserWithFirebase(String email, String password, Button l) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(requireContext().getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                logInUser(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()));
            } else {
                l.setEnabled(true);
                Toast.makeText(requireContext().getApplicationContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logInUser(FirebaseUser user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USERS).document(user.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String role = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get(ROLE)).toString()));
                if (role.equals(ROLE_ADMIN)) {
                    requireContext().startActivity(new Intent(requireContext(), AdminActivity.class));
                    requireActivity().finish();
                } else if (role.equals(ROLE_CUSTOMER)) {
                    requireContext().startActivity(new Intent(requireContext(), MainActivity.class));
                    requireActivity().finish();
                } else {
                    requireContext().startActivity(new Intent(requireContext(), MainActivity.class));
                    requireActivity().finish();
                }
            } else {
                Toast.makeText(requireContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
