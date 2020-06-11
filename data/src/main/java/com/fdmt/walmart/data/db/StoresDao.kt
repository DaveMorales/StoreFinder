package com.fdmt.walmart.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.fdmt.walmart.domain.stores.entity.Store

@Dao
interface StoresDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertStore(bookmark: Store)

    @Insert(onConflict = REPLACE)
    suspend fun insertStores(stores: List<Store>)

    @Query("SELECT * FROM stores")
    suspend fun getAllStores(): List<Store>

    @Query("SELECT * FROM stores WHERE zipCode = :zipCode")
    suspend fun getStoreByZip(zipCode: String): List<Store>

    @Delete
    suspend fun deleteStore(bookmark: Store)

    // @Query("SELECT * FROM stores ORDER BY ((latPoint-:latitude)*(latPoint-:latitude)) + ((lonPoint - :longitude)*(lonPoint - :longitude)) ASC limit 10")
    @Query("SELECT * FROM stores WHERE :CUR_sin_lat * sinLat + :CUR_cos_lat * cosLat * (cosLng* :CUR_cos_lng + sinLng * :CUR_sin_lng) > :cos_allowed_distance")
    suspend fun findNearest(CUR_sin_lat: Double, CUR_cos_lat: Double, CUR_cos_lng: Double, CUR_sin_lng: Double, cos_allowed_distance: Double): List<Store>

    @Query("DELETE FROM stores")
    fun nukeTable()
}