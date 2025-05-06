package com.example.tamagotchi.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tamagotchi.database.entities.User;

import java.util.List;

@Dao
public interface userDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... users);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM "+ database.USER_TABLE+" ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE FROM " + database.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + database.USER_TABLE+" WHERE username == :username")
    LiveData<User> getUserByName(String username);


    @Query("SELECT * FROM " + database.USER_TABLE + " WHERE username = :username AND password = :password LIMIT 1")
    User getUserByCredentials(String username, String password);

    @Query("SELECT * FROM " + database.USER_TABLE + " WHERE id = :userId LIMIT 1")
    LiveData<User> getUserById(int userId);


}
