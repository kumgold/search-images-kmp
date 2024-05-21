package com.example.search_images_kmp.di

import android.content.Context
import com.example.search_images_kmp.cache.Database
import com.example.search_images_kmp.di.cache.AndroidDatabaseDriverFactory
import com.example.search_images_kmp.network.KakaoApi

actual class Factory(context: Context) {
    private val databaseDriverFactory = AndroidDatabaseDriverFactory(context)

    actual fun createApi(): KakaoApi {
        return commonCreateApi()
    }

    actual fun createDatabase(): Database {
        return commonDatabase(databaseDriverFactory)
    }
}