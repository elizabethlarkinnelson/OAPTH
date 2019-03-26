package com.onramp.android.takehome;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ItemRoomDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();
    private static ItemRoomDatabase INSTANCE;

    static ItemRoomDatabase getDatabase(final Context context){
        //Next three lines prevent multiple datbases being opened
        if (INSTANCE == null){
            synchronized (ItemRoomDatabase.class){
                if (INSTANCE == null){
                    //Builds db
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
        String[] items = {"mocha", "latte", "iced team"};

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
