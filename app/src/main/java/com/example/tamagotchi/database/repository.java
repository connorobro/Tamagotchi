package com.example.tamagotchi.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tamagotchi.database.entities.User;

import java.util.List;

public class repository {

    private static repository instance;
    private final com.example.tamagotchi.database.userDAO userDAO;

    private repository(Application application) {
        database db = database.getInstance(application);
        userDAO = db.userDAO();
    }

    public static synchronized repository getRepository(Application application) {
        if (instance == null) {
            instance = new repository(application);
        }
        return instance;
    }


    public LiveData<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByName(username);
    }

    public LiveData<User> getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public void insert(User... users) {
        database.databaseWriteExecutor.execute(() -> userDAO.insert(users));
    }

    public void delete(User user) {
        database.databaseWriteExecutor.execute(() -> userDAO.delete(user));
    }
}