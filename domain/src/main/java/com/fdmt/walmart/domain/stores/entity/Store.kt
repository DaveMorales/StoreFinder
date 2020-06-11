package com.fdmt.walmart.domain.stores.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fdmt.walmart.domain.base.Response
import com.google.android.gms.maps.model.LatLng


@Entity(tableName = "stores")
data class Store(

    @PrimaryKey(autoGenerate = false)
    val storeID: String,

    @ColumnInfo(name = "name")
    val storeName: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "zipCode")
    val zipCode: String,

    @ColumnInfo(name = "telephone")
    val telephone: String,

    @ColumnInfo(name = "manager")
    val manager: String,

    @ColumnInfo(name = "opens")
    val opens: String,

    @ColumnInfo(name = "latPoint")
    val latPoint: Double,

    @ColumnInfo(name = "lonPoint")
    val lonPoint: Double,

    @ColumnInfo(name = "cosLat")
    val cosLat: Double,

    @ColumnInfo(name = "sinLat")
    val sinLat: Double,

    @ColumnInfo(name = "cosLng")
    val cosLng: Double,

    @ColumnInfo(name = "sinLng")
    val sinLng: Double,

    @ColumnInfo(name = "LatLng")
    val latLng: LatLng

    ) : Response