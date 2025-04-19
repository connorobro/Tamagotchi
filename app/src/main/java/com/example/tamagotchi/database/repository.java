package com.example.tamagotchi.database;

import android.app.Application;

public class repository {
    //private final UserDAO userDAO;

    private static repository repo;

    private repository(Application application){
        database db = database.getInstance(application);
        //this.userDAO= db.userDAO();
        //need to fix the MiniGameActivity becasue making the abstarct for userDao causes an error in their
        //
    }

}
