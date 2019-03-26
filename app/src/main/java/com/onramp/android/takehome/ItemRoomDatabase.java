package com.onramp.android.takehome;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

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
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
