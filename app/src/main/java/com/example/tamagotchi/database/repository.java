package com.example.tamagotchi.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tamagotchi.database.entities.Pet;
import com.example.tamagotchi.database.entities.User;

import java.util.List;

public class repository {

    private static repository instance;

    private final petDAO petDAO;
    private final com.example.tamagotchi.database.userDAO userDAO;

    private repository(Application application) {
        database db = database.getInstance(application);
        userDAO = db.userDAO();
        petDAO = db.petDAO();
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
    public LiveData<List<Pet>> getAllPets() {
        return petDAO.getAllPets();
    }

    public LiveData<Pet> getPetById(int petId) {
        return petDAO.getPetById(petId);
    }

    public LiveData<Pet> getPetByName(String name) {
        return petDAO.getPetByName(name);
    }

    public void insertPets(Pet... pets) {
        database.databaseWriteExecutor.execute(() -> petDAO.insert(pets));
    }

    public void deletePet(Pet pet) {
        database.databaseWriteExecutor.execute(() -> petDAO.delete(pet));
    }

    public void deleteAllPets() {
        database.databaseWriteExecutor.execute(petDAO::deleteAll);
    }

}