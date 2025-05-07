package com.example.tamagotchi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamagotchi.database.entities.Pet;
import com.example.tamagotchi.database.repository;

public class admin_option extends AppCompatActivity {

    private repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_option);

        repo = repository.getRepository(getApplication());

        EditText petNameInput = findViewById(R.id.petNameInput);
        EditText petSpeciesInput = findViewById(R.id.petSpeciesInput);
        Button addPetButton = findViewById(R.id.confirmAddPetButton);
        Button nevermindButton = findViewById(R.id.nevermindButton);  // "Nevermind" button

        // Handle the "Add Pet" button click
        addPetButton.setOnClickListener(v -> {
            String name = petNameInput.getText().toString().trim();
            String species = petSpeciesInput.getText().toString().trim();

            if (name.isEmpty() || species.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Pet newPet = new Pet(name, species);
            repo.insertPet(newPet);
            Toast.makeText(this, "Pet added!", Toast.LENGTH_SHORT).show();
            finish(); // Close this activity and return to the previous activity
        });

        // Handle the "Nevermind" button click to go back to the previous screen
        nevermindButton.setOnClickListener(v -> {
            finish();
        });
    }
}
