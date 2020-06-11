package com.fdmt.walmart.data.db

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson

class LatLngConverter {

    companion object {

        val gson = Gson()

        @TypeConverter
        @JvmStatic
        fun fromLatLng(value: LatLng): String {
            return "{" +
                    "\"latitude\":  ${value.latitude}, " +
                    "\"longitude\":  ${value.longitude} " +
                    "}"
        }

        @TypeConverter
        @JvmStatic
        fun toLatLng(json: String): LatLng {
            return gson.fromJson(json,LatLng::class.java)
        }
    }

}