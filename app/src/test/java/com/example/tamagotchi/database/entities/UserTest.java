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
        User user1 = new User("admin", "admin", true);
        user1.setUsername("realAdmin");
        assertEquals(user1.getUsername(), "realAdmin");
        assertNotEquals(user1.getUsername(), "fakeAdmin");

    }
    
    @Test
    public void testSetPassword() {
        User user1 = new User("admin", "admin", true);
        user1.setPassword("admin");
        assertEquals(user1.getPassword(), "admin");
    }
}