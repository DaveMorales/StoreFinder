package com.fdmt.walmart.data.stores.remote

import com.fdmt.walmart.domain.stores.entity.StoresResponse
import retrofit2.http.GET

interface StoresApi {

    @GET("address/storeLocatorCoordinates")
    suspend fun getStores(): StoresResponse

}