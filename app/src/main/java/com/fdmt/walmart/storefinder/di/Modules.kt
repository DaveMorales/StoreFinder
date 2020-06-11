package com.fdmt.walmart.storefinder.di

import androidx.room.Room
import com.fdmt.walmart.data.db.StoreFinderDatabase
import com.fdmt.walmart.data.network.BaseHttpClient
import com.fdmt.walmart.data.network.BaseRetrofit
import com.fdmt.walmart.data.network.LatLngDeserializer
import com.fdmt.walmart.data.network.ResponseHandler
import com.fdmt.walmart.data.stores.remote.StoresApi
import com.fdmt.walmart.data.stores.repository.StoresRepositoryImpl
import com.fdmt.walmart.domain.stores.entity.Store
import com.fdmt.walmart.domain.stores.usecase.GetStoreListUsecase
import com.fdmt.walmart.presentation.stores.StoresSharedViewModel
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.Executors

private val NETWORK_IO = Executors.newFixedThreadPool(5)

val viewModelModule = module {

    single { provideAPI(get()) }

    single { ResponseHandler() }

    factory { GetStoreListUsecase(StoresRepositoryImpl(get(), get(), get())) }

    viewModel { StoresSharedViewModel(get()) }

}

fun provideAPI(baseRetrofit: BaseRetrofit): StoresApi =
    baseRetrofit.retrofit.create(StoresApi::class.java)

val netModule = module {

    single { provideGson() }

    single { BaseHttpClient() }

    single { okHttpClient(get()) }

    single { BaseRetrofit(get(), get()) }

    single { provideRetrofit(get()) }

}

fun provideGson(): Gson = GsonBuilder()
    .registerTypeAdapter(Store::class.java, LatLngDeserializer())
    .create()


fun okHttpClient(baseHttpClient: BaseHttpClient): OkHttpClient = baseHttpClient.okHttpClient

fun provideRetrofit(baseRetrofit: BaseRetrofit): Retrofit = baseRetrofit.retrofit

val dbModule = module {

    single {
        Room.databaseBuilder(androidContext(), StoreFinderDatabase::class.java, "store_finder")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<StoreFinderDatabase>().storesDao() }

}

val storeMainModule = module {

    single { LocationServices.getFusedLocationProviderClient(androidContext()) }

}