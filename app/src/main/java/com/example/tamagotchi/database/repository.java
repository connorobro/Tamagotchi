package com.example.tamagotchi.database;

import android.app.Application;

public class repository {
    //private final UserDAO userDAO;

    private static repository repo;
    private final Object userDAO;

    private repository(Application application){
        database db = database.getInstance(application);
        this.userDAO= db.userDAO();

    }

}
