package com.example.tamagotchi.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tamagotchi.database.entities.Pet;
import com.example.tamagotchi.database.entities.User;

import java.util.List;

public class repository {

    private static repository instance;

    private final userDAO userDao;
    private final petDAO petDao;

    private repository(Application application) {
        database db = database.getInstance(application);
        userDao = db.userDAO();
        petDao = db.petDAO();
    }

    public static synchronized repository getRepository(Application application) {
        if (instance == null) {
            instance = new repository(application);
        }
        return instance;
    }

    // User operations
    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDao.getUserByName(username);
    }

    public LiveData<User> getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    public void insertUsers(User... users) {
        database.databaseWriteExecutor.execute(() -> userDao.insert(users));
    }

    public void updateUser(User user) {
        database.databaseWriteExecutor.execute(() -> userDao.update(user));
    }

    public void deleteUser(User user) {
        database.databaseWriteExecutor.execute(() -> userDao.delete(user));
    }

    // Pet operations
    public LiveData<List<Pet>> getAllPets() {
        return petDao.getAllPets();
    }

    public LiveData<Pet> getPetById(int petId) {
        return petDao.getPetById(petId);
    }

    public LiveData<Pet> getPetByName(String name) {
        return petDao.getPetByName(name);
    }

    public void insertPet(Pet pet) {
        database.databaseWriteExecutor.execute(() -> petDao.insert(pet));
    }

    public void deletePet(Pet pet) {
        database.databaseWriteExecutor.execute(() -> petDao.delete(pet));
    }

    public void deleteAllPets() {
        database.databaseWriteExecutor.execute(petDao::deleteAll);
    }
}
