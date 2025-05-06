package com.example.tamagotchi.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.tamagotchi.database.database;

@Entity(tableName = database.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String password;
    private boolean isAdmin;
    private int food;

    public User() {
        this.food = 0;
    }

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.food = 0;
    }

//    public User(String username, String password) {
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }
}
