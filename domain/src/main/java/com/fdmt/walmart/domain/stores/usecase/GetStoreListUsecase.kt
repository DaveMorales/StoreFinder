package com.fdmt.walmart.domain.stores.usecase

import com.fdmt.walmart.domain.stores.repository.StoreRepository

class GetStoreListUsecase(private val storeRepository: StoreRepository) {

    suspend operator fun invoke(params: getStoresParams) = storeRepository.getStores(params.latitude, params.longitude)
}

class getStoresParams(val latitude: Double, val longitude: Double)


