package com.example.tamagotchi.database.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import com.example.tamagotchi.database.entities.Pet;

public class PetTest {
    @Before
    public void setUp() throws Exception {
        Pet pe = new Pet("gob","dog");
    }

    @Test
    public void getname()
    {
        Pet pe = new Pet("gob","dog");
        assertEquals(pe.getName(),"gob");
    }
    @Test
    public void setName()
    {
        Pet pe = new Pet("gob","dog");
        pe.setName("John");
        assertEquals(pe.getName(),"John");
        assertNotEquals(pe.getName(),"bill");
    }
    @Test
    public void getspec()
    {
        Pet pe = new Pet("gob","dog");
        assertEquals(pe.getSpecies(),"dog");
    }
    @Test
    public void setspec()
    {
        Pet pe = new Pet("gob","dog");
        pe.setSpecies("Cat");
        assertEquals(pe.getSpecies(), "Cat");
    }
    @Test
    public void getid()
    {
        Pet pe = new Pet("gob","dog");
        assertEquals(pe.getPetId(),0);

    }
    @Test
    public void setId()
    {
        Pet pe = new Pet("gob","dog");
        pe.setPetId(1);
        assertEquals(pe.getPetId(),1);
    }
}
