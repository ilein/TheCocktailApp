package com.ilein.thecocktailapp

import android.app.Application
import com.ilein.thecocktailapp.di.databaseModule
import com.ilein.thecocktailapp.di.networkModule
import com.ilein.thecocktailapp.di.useCaseModule
import com.ilein.thecocktailapp.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin(){
            androidLogger()
            androidContext(this@App)
            modules(databaseModule, networkModule, useCaseModule, vmModule)
        }
    }
}