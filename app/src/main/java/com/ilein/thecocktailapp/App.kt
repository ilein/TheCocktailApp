package com.ilein.thecocktailapp

import android.app.Application
import com.ilein.thecocktailapp.koin.databaseModule
import com.ilein.thecocktailapp.koin.networkModule
import com.ilein.thecocktailapp.koin.vmModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin(){
            androidLogger()
            androidContext(this@App)
            modules(databaseModule, networkModule, vmModule)
        }
    }
}