package com.example.meped

import android.app.Application
import com.example.meped.di.AppContainer

class MepedApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}