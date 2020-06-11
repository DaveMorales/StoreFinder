package com.fdmt.walmart.data.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseRetrofit constructor(
    okHttpClient: OkHttpClient,
    gson: Gson
) {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.walmartmobile.com.mx/walmart-services/mg/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

}