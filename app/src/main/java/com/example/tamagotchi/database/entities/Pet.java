package com.example.tamagotchi.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.tamagotchi.database.database;

@Entity(tableName = database.PET_TABLE)
public class Pet {
    @PrimaryKey(autoGenerate = true)
    private int petId;

    private String name;

    private String species;

    public Pet(String name, String species) {
        this.name = name;
        this.species = species;
    }


    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
