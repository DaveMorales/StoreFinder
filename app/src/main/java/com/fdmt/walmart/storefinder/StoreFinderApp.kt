package com.fdmt.walmart.storefinder

import android.app.Application
import com.facebook.stetho.Stetho
import com.fdmt.walmart.storefinder.di.dbModule
import com.fdmt.walmart.storefinder.di.netModule
import com.fdmt.walmart.storefinder.di.storeMainModule
import com.fdmt.walmart.storefinder.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StoreFinderApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)


        startKoin {
            androidContext(this@StoreFinderApp)
            modules(
                viewModelModule,
                netModule,
                dbModule,
                storeMainModule
            )
        }
    }
}