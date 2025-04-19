package com.example.tamagotchi.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tamagotchi.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version =1, exportSchema = false)
public abstract class database extends RoomDatabase {
    public static final String USER_TABLE ="usertable" ;
    private static final String DATABASE_NAME="Tamagotchi";

    private static volatile database INSTANCE;

    private static final int NUMBER_OF_THREADS=4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //Once the addDefault values is added this should work

    public static database getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    database.class,
                                    DATABASE_NAME)
                            .fallbackToDestructiveMigration() // wipes data if migration not found
                            .addCallback(databaseCallback) // use this to pre-populate if needed
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static final RoomDatabase.Callback databaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(() -> {
                        // TODO: Insert default data like admin and test users here
                        Log.d("DB", "Database created. You can add default users here.");
                    });
                }
            };


}
