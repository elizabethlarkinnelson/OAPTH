package com.onramp.android.takehome;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/*
 * Using database annotation to create a database with one table, item
 */
@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ItemRoomDatabase extends RoomDatabase {

    /*
     *Dao that works with database as getter
     */
    public abstract ItemDao itemDao();
    private static ItemRoomDatabase INSTANCE;

    static ItemRoomDatabase getDatabase(final Context context){
        /*
         * Preventing multiple database instances from opening
         */
        if (INSTANCE == null){
            synchronized (ItemRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDatabase.class,
                            "item_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final ItemDao mDao;
        String[] items = {"Mocha", "Latte", "Vanilla Latte", "Soy Latte", "Hot Chocolate",
                        "Iced Tea", "Hot Tea", "London Fog", "Hot Chai", "Iced Chai"};

        PopulateDbAsync(ItemRoomDatabase db){
            mDao = db.itemDao();
        }

        @Override
        protected Void doInBackground(final Void... params){
            mDao.deleteAll();

            for (int i = 0; i <= items.length - 1; i++){
                Item item = new Item(items[i]);
                mDao.insert(item);
            }

            return null;
        }
    }
}
