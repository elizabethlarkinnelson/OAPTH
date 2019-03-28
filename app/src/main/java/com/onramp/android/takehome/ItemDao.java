package com.onramp.android.takehome;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


/*
 * DAO = Data access object
 * This annotation provides shorthand access to SQL queries
 */
@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Query("DELETE FROM item_table")
    void deleteAll();

    /*
     * LiveData is dataholder class that is lifecycle aware
     */

    @Query("SELECT * FROM item_table ORDER BY item ASC")
    LiveData<List<Item>> getAllItems();
}
