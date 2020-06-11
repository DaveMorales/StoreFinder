package com.fdmt.walmart.data.stores.repository

import com.fdmt.walmart.data.db.StoresDao
import com.fdmt.walmart.data.network.ResponseHandler
import com.fdmt.walmart.data.stores.remote.StoresApi
import com.fdmt.walmart.domain.base.Resource
import com.fdmt.walmart.domain.base.Response
import com.fdmt.walmart.domain.stores.repository.StoreRepository
import java.lang.Math.*

class StoresRepositoryImpl(
    private val storesApi: StoresApi,
    private val responseHandler: ResponseHandler,
    private val storesDao: StoresDao
) : StoreRepository {

    override suspend fun getStores(latitude: Double, longitude: Double): Resource<List<Response>> {
        return try {
            val response = storesApi.getStores()

            // storesDao.nukeTable()
            storesDao.insertStores(response.responseArray)

            val cosLat = cos(latitude * PI / 180)
            val sinLat = sin(latitude * PI / 180)
            val cosLng = cos(longitude * PI / 180)
            val sinLng = sin(longitude * PI / 180)
            val allowedDistance = cos(20.0 / 6371)

            val nearestStores = storesDao.findNearest(sinLat,cosLat,cosLng,sinLng,allowedDistance)

            return responseHandler.handleSuccess(nearestStores)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}