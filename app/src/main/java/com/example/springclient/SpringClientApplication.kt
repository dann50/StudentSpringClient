package com.example.springclient

import android.app.Application
import com.example.springclient.data.AppContainer
import com.example.springclient.data.DefaultAppContainer

class SpringClientApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}