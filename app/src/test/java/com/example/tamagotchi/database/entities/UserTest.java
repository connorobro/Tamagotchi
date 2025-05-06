package com.example.tamagotchi.database.entities;

import junit.framework.TestCase;
import static org.junit.Assert.assertNotEquals;

public class UserTest extends TestCase {

    public void setUp() throws Exception {
        User user1 = new User("admin", "admin", true);
    }

    public void testSetUsername() {
        User user1 = new User("admin", "admin", true);
        user1.setUsername("realAdmin");
        assertEquals(user1.getUsername(), "realAdmin");
        assertNotEquals(user1.getUsername(), "fakeAdmin");

    }

    public void testSetPassword() {
        User user1 = new User("admin", "admin", true);
        user1.setPassword("admin");
        assertEquals(user1.getPassword(), "admin");
        assertNotEquals(user1.getPassword(), "evilAdmin");
    }
}