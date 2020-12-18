package com.example.inuapp.login.create;

import android.annotation.SuppressLint;
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
import com.example.inuapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;

import static com.example.inuapp.models.User.ROLE;
import static com.example.inuapp.models.User.ROLE_ADMIN;
import static com.example.inuapp.models.User.ROLE_CUSTOMER;
import static com.example.inuapp.models.User.USERS;
import static com.example.inuapp.models.User.USER_DETAILS;


public class CreatePage extends Fragment {

    public static CreatePage newInstance() {
        return new CreatePage();
    }

    private final User user = new User();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.create_page_fragment, container, false);

        EditText emailCr = v.findViewById(R.id.emailCr);
        EditText passwordCr = v.findViewById(R.id.passwordCr);
        EditText nameCr = v.findViewById(R.id.nameCr);

        Button createB = v.findViewById(R.id.createB);
        createB.setOnClickListener(v1 -> {
            if (validateForm(emailCr, passwordCr, nameCr)) {
                createNewUser(emailCr.getText().toString(), passwordCr.getText().toString(),createB);
                createB.setEnabled(false);
            } else {
                Toast.makeText(requireContext(), "Check details", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private boolean validateForm(EditText email, EditText pass, EditText name) {
        boolean valid = false;
        if (!email.getText().toString().contains("@")) {
            email.setError("Wrong email format");
            email.requestFocus();
        } else if (pass.getText().toString().length() < 6) {
            pass.setError("Should be more than 6 characters");
            pass.requestFocus();
        } else if (name.getText().toString().isEmpty()) {
            name.setError("Name is empty");
            name.requestFocus();
        } else {
            user.setEmail(email.getText().toString());
            user.setRole(User.ROLE_CUSTOMER);
            user.setName(name.getText().toString());
            valid = true;
        }
        return valid;
    }

    private void createNewUser(String email, String pass,Button createB) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user.setUserId(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid());
                Toast.makeText(requireContext(), "Welcome", Toast.LENGTH_SHORT).show();
                saveUserToDb(user);
            } else {
                createB.setEnabled(true);
                Toast.makeText(requireContext(), Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("ShowToast")
    private void saveUserToDb(User user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USERS).document(user.getUserId()).set(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(requireContext().getApplicationContext(), "Details saved", Toast.LENGTH_SHORT).show();
                logInUser(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()));
            } else {
                Toast.makeText(requireContext().getApplicationContext(), "Failed to save details", Toast.LENGTH_SHORT).show();
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser != null;
                firebaseUser.delete().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(requireContext().getApplicationContext(), "User removed, Create new account", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext().getApplicationContext(), "Failed to remove user, Use different details", Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }

    private void logInUser(FirebaseUser user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USERS).document(user.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String role = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get(ROLE)).toString());
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
