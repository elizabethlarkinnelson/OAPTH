package com.onramp.android.takehome;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/*
 * @ = Annotations
 * This item class uses entity to create db
 * This db has one column called "item" in the "item_table"
 */
@Entity(tableName = "item_table")
public class Item {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "item")
    private String mItem;

    /*
     * Constructor.  Non null means parameter can never be null
     */
    public Item(@NonNull String item) {
        this.mItem = item;
    }
    public String getItem(){
        return this.mItem;
    }
}
