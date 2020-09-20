package com.example.paisesapp

import android.app.Application
import com.example.paisesapp.di.Module.moduleDI
import com.example.paisesapp.di.Module.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CountryApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@CountryApplication)
            modules(listOf(moduleDI, networkModule))
        }
    }
}