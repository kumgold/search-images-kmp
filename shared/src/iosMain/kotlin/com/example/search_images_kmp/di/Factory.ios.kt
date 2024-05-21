package com.example.search_images_kmp.di

import com.example.search_images_kmp.cache.Database
import com.example.search_images_kmp.di.cache.IOSDatabaseDriverFactory
import com.example.search_images_kmp.network.KakaoApi

actual class Factory {
    private val databaseDriverFactory = IOSDatabaseDriverFactory()

    actual fun createApi(): KakaoApi {
        return commonCreateApi()
    }

    actual fun createDatabase(): Database {
        return commonDatabase(databaseDriverFactory)
    }
}