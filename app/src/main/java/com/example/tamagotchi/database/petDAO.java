package com.example.tamagotchi.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tamagotchi.database.entities.Pet;
import com.example.tamagotchi.database.entities.User;

import java.util.List;

@Dao
public interface petDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pet pet);

    @Delete
    void delete(Pet pet);

    @Query("SELECT * FROM "+ database.PET_TABLE+" ORDER BY name")
    LiveData<List<Pet>> getAllPets();

    @Query("DELETE FROM " + database.PET_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + database.PET_TABLE+" WHERE name == :name")
    LiveData<Pet> getPetByName(String name);



    @Query("SELECT * FROM " + database.PET_TABLE + " WHERE petId = :petId LIMIT 1")
    LiveData<Pet> getPetById(int petId);

}
