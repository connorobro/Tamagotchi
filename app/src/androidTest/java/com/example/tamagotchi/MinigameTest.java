package com.example.tamagotchi;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

@RunWith(AndroidJUnit4.class)
public class MinigameTest {

    @Test
    public void testLaunchMinigame_noCrashes() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                try {
                    Method launch = MainActivity.class
                            .getDeclaredMethod("launchMinigame");
                    launch.setAccessible(true);
                    launch.invoke(activity);
                } catch (Exception e) {
                    throw new AssertionError("launchMinigame() threw", e);
                }
            });
        }
    }
