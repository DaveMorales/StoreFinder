package com.fdmt.walmart.domain.stores.repository

import com.fdmt.walmart.domain.base.Resource
import com.fdmt.walmart.domain.base.Response

interface StoreRepository {

    suspend fun getStores(latitude: Double, longitude: Double): Resource<List<Response>>

}