package com.example.inuapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inuapp.models.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.inuapp.models.User.EMAIL;
import static com.example.inuapp.models.User.NAME;
import static com.example.inuapp.models.User.ROLE;
import static com.example.inuapp.models.User.UID;
import static com.example.inuapp.models.User.USERS;
import static com.example.inuapp.models.User.USER_DETAILS;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> userMutableLiveData;

    private MutableLiveData<User> getUserData(String id) {
        userMutableLiveData = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USERS).document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String role = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get(ROLE)).toString());
                String username = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get(NAME)).toString());
                String email = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get(EMAIL)).toString());
                String uid = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get(UID)).toString());
                User u = new User(uid, username, email, role);
                userMutableLiveData.setValue(u);
            } else {
                System.out.println(Objects.requireNonNull(task.getException()).toString());
                String fail = "failed to get data";
                userMutableLiveData.setValue(new User(fail, fail, fail, fail));
            }
        });
        return userMutableLiveData;
    }

    public LiveData<User> getUsersData (String id) {
        return getUserData(id);
    }
}
