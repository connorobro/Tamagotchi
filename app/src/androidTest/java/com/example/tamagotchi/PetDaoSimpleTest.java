package com.example.tamagotchi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.tamagotchi.database.database;
import com.example.tamagotchi.database.entities.Pet;
import com.example.tamagotchi.database.petDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class PetDaoSimpleTest {

    private database db;
    private petDAO petDao;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, database.class)
                .allowMainThreadQueries()
                .build();
        petDao = db.petDAO();
    }

    @After
    public void tearDown() {
        db.close();
    }

    /**
     * Utility to get the value from a LiveData object in an instrumented test.
     * Observes on the main thread.
     */
    private <T> T getOrAwaitValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> liveData.observeForever(o -> {
            data[0] = o;
            latch.countDown();
        }));

        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw new RuntimeException("LiveData value was never set.");
        }

        return (T) data[0];
    }

    @Test
    public void insertPet_savesToDatabase() throws Exception {
        Pet pet = new Pet("Milo", "Dog");
        petDao.insert(pet);

        Pet result = getOrAwaitValue(petDao.getPetByName("Milo"));
        assertNotNull(result);
        assertEquals("Milo", result.getName());
        assertEquals("Dog", result.getSpecies());
    }

    @Test
    public void deletePet_removesFromDatabase() throws Exception {
        Pet pet = new Pet("Zoe", "Cat");
        petDao.insert(pet);

        Pet inserted = getOrAwaitValue(petDao.getPetByName("Zoe"));
        assertNotNull(inserted);

        petDao.delete(inserted);

        Pet deleted = getOrAwaitValue(petDao.getPetByName("Zoe"));
        assertNull(deleted);
    }
}
