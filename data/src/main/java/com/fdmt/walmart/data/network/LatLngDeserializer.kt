package com.fdmt.walmart.data.network

import com.fdmt.walmart.domain.stores.entity.Store
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.Math.*
import java.lang.reflect.Type


class LatLngDeserializer : JsonDeserializer<Store> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Store {

        val jsonObject = json!!.asJsonObject

        val latPoint = jsonObject.get("latPoint").asString.toDouble()
        val lonPoint = jsonObject.get("lonPoint").asString.toDouble()

        return Store(
            storeID = jsonObject.get("storeID").asString,
            storeName = jsonObject.get("name").asString,
            address = jsonObject.get("address").asString,
            zipCode = jsonObject.get("zipCode").asString,
            telephone = jsonObject.get("telephone").asString,
            manager = jsonObject.get("manager").asString,
            opens = jsonObject.get("opens").asString,
            lonPoint = jsonObject.get("lonPoint").asString.toDouble(),
            latPoint = jsonObject.get("latPoint").asString.toDouble(),
            cosLat = cos(latPoint * PI / 180),
            sinLat = sin(latPoint * PI / 180),
            cosLng = cos(lonPoint * PI / 180),
            sinLng = sin(lonPoint * PI / 180),
            latLng = LatLng(latPoint, lonPoint)
        )
    }


}