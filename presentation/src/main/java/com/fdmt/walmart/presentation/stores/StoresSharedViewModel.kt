package com.fdmt.walmart.presentation.stores

import android.location.Location
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.*
import com.fdmt.walmart.domain.base.Resource
import com.fdmt.walmart.domain.stores.usecase.GetStoreListUsecase
import com.fdmt.walmart.domain.stores.usecase.getStoresParams
import com.fdmt.walmart.presentation.base.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers

class StoresSharedViewModel(private val getStoreListUseCase: GetStoreListUsecase) :
    BaseViewModel() {

    private val request = MutableLiveData<Location>()
    var locationUpdates = MutableLiveData<LatLng>()
    var pageChange = MutableLiveData<Int>()

    var stores = request.switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(
                getStoreListUseCase(
                    getStoresParams(
                        it.latitude,
                        it.longitude
                    )
                )
            )
        }

    }

    fun updateLocation(location: Location) {
        locationUpdates.postValue(LatLng(location.latitude, location.longitude))
        request.value = location
    }

    fun changePage(page: Int){
        pageChange.value = page
    }


}