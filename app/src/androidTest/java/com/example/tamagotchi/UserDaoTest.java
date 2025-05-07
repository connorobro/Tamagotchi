package com.example.tamagotchi;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;


import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.tamagotchi.database.database;
import com.example.tamagotchi.database.entities.User;
import com.example.tamagotchi.database.userDAO;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class UserDaoTest {

    private userDAO userdao;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        database db = Room.inMemoryDatabaseBuilder(context, database.class)
                .allowMainThreadQueries()
                .build();
        userdao = db.userDAO();
    }
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
    public void insertUser_savesToDatabase() throws Exception {
        User user = new User("admin", "admin", false);
        userdao.insert(user);
        User result = getOrAwaitValue(userdao.getUserByName("admin"));
        assertNotNull(result);
        assertEquals("admin", result.getUsername());
        assertEquals("admin",result.getPassword() );
    }
    public void deleteUser_removesFromDatabase() throws Exception {
        User user = new User("admin", "admin", false);
        userdao.insert(user);

        User inserted = getOrAwaitValue(userdao.getUserByName("admin"));
        assertNotNull(inserted);

        userdao.delete(inserted);

        User deleted = getOrAwaitValue(userdao.getUserByName("admin"));
        assertNull(deleted);
    }

}