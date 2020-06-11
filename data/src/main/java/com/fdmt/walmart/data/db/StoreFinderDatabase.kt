package com.fdmt.walmart.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fdmt.walmart.domain.stores.entity.Store

@Database(
    entities = [Store::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LatLngConverter::class)
abstract class StoreFinderDatabase : RoomDatabase() {

    abstract fun storesDao(): StoresDao
}