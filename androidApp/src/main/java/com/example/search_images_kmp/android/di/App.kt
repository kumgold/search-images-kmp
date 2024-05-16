package com.example.search_images_kmp.android.di

import android.app.Application
import com.example.search_images_kmp.di.AppContainer
import com.example.search_images_kmp.di.Factory

class App : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(Factory())
    }
}