package com.example.tamagotchi.database.entities;

import static org.junit.Assert.assertNotEquals;

import junit.framework.TestCase;

import org.junit.Test;

public class UserTest extends TestCase {

    public void setUp() throws Exception {
        User user1 = new User("admin", "admin", true );
    }

    @Test
    public void testSetUsername() {
        User user1 = new User("admin", "admin", true );
        user1.setUsername("admin2");
        assertEquals(user1.getUsername(), "admin2");
        assertNotEquals(user1.getUsername(), "admin3");
    }

    @Test
    public void testSetPassword() {
        User user1 = new User("admin", "admin", true );
        user1.setPassword("realAdmin");
        assertEquals(user1.getPassword(), "realAdmin");
        assertNotEquals(user1.getPassword(), "evilAdmin");
    }
}