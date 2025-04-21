package com.example.tamagotchi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.tamagotchi.database.entities.User;
import com.example.tamagotchi.databinding.ActivitySignupBinding;

public class SignUpActivity extends AppCompatActivity {
    private com.example.tamagotchi.database.repository repository;
    private ActivitySignupBinding binding;
    public static final String SHARED_PREFERENCE_FILE_KEY = "com.example.tamagotchi.PREFERENCE_FILE_KEY";
    public static final String SHARED_PREFERENCE_USERID_KEY = "com.example.tamagotchi.PREFERENCE_USERID_KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = repository.getRepository(getApplication());
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupUser();
            }
        });
    }
    private void signupUser(){
        String username = binding.userNameEditText.getText().toString();
        String password = binding.passwordSignUpEditText.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            toastMaker("Username and password  not be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                toastMaker("error");
                } else {
                    User otheruser = new User(username, password);
                    repository.getUserById(otheruser.getId());
                    SharedPreferences preferences = getApplicationContext()
                            .getSharedPreferences(SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
                    preferences.edit().putInt(SHARED_PREFERENCE_USERID_KEY, user.getId()).apply();
                    // Start MainActivity and pass the user ID
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                    finish();
                }
        });
    }
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public static Intent signUpIntentFactory(Context context) {
        return new Intent(context, SignUpActivity.class);
    }
}

