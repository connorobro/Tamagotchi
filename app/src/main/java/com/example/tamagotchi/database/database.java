package com.example.tamagotchi.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tamagotchi.database.entities.Pet;
import com.example.tamagotchi.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Pet.class}, version = 3, exportSchema = false)
public abstract class database extends RoomDatabase {
    public static final String USER_TABLE = "usertable";
    public static final String PET_TABLE  = "pettable";
    private static final String DATABASE_NAME = "Tamagotchi";

    private static volatile database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // carlos I did this to add food column without wiping existing data
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
            db.execSQL("ALTER TABLE " + USER_TABLE +
                    " ADD COLUMN food INTEGER NOT NULL DEFAULT 0");
        }
    };

    public static database getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    database.class,
                                    DATABASE_NAME)
                            .addMigrations(MIGRATION_2_3)
                            .fallbackToDestructiveMigration()
                            .addCallback(databaseCallback)
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
                        userDAO dao = INSTANCE.userDAO();
                        petDAO petDao = INSTANCE.petDAO();

                        petDao.deleteAll();
                        dao.deleteAll();

                        // Seed users with starting food = 20
                        User admin = new User("admin", "admin", true);
                        dao.insert(admin);
                        User testUser = new User("testUser", "testUser", false);
                        dao.insert(testUser);

                        // Seed pets
                        Pet dog = new Pet("Rob", "Dog");
                        Pet cat = new Pet("Bob", "Cat");
                        petDao.insert(dog);
                        petDao.insert(cat);

                        Log.d("DB", "Database created with default users and food.");
                    });
                }
            };

    public abstract userDAO userDAO();
    public abstract petDAO petDAO();
}
