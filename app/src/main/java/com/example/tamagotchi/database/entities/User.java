package com.example.tamagotchi.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.tamagotchi.database.database;

@Entity(tableName= database.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;
}
