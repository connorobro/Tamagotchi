package com.example.tamagotchi.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.tamagotchi.database.entities.User;

import java.util.List;

/**
 * Data Access Object (DAO) for the User entity.
 *
 * Provides methods for accessing and manipulating User data in the Room database.
 */
@Dao
public interface UserDAO {

    /**
     * Retrieves a user from the database by their unique ID.
     *
     * @param userId The primary key (ID) of the user.
     * @return The User object if found, otherwise null.
     */
    @Query("SELECT * FROM user WHERE id = :userId LIMIT 1")
    User getUserById(int userId);

    /**
     * Retrieves a user from the database by their username.
     *
     * @param username The username of the user.
     * @return The User object if found, otherwise null.
     */
    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);

    /**
     * Inserts a new user into the database.
     *
     * @param user The User object to be inserted.
     */
    @Insert
    void insert(User user);

    /**
     * Updates an existing user in the database.
     *
     * @param user The User object with updated information.
     */
    @Update
    void update(User user);

    /**
     * Deletes a specific user from the database.
     *
     * @param user The User object to be removed.
     */
    @Delete
    void delete(User user);

    /**
     * Deletes all user records from the database.
     * Typically used when initializing the database.
     */
    @Query("DELETE FROM user")
    void deleteAll();

    /**
     * (Optional) Retrieves a list of all users from the database.
     *
     * @return A list of User objects.
     */
    @Query("SELECT * FROM user")
    List<User> getAllUsers();
}
