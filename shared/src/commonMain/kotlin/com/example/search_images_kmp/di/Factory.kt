package com.example.search_images_kmp.di

import com.example.search_images_kmp.cache.Database
import com.example.search_images_kmp.cache.DatabaseDriverFactory
import com.example.search_images_kmp.network.KakaoApi
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect class Factory {
    fun createApi(): KakaoApi
    fun createDatabase(): Database
}

internal fun commonDatabase(driverFactory: DatabaseDriverFactory): Database {
    return Database(driverFactory)
}

internal fun commonCreateApi(): KakaoApi {
    return KakaoApi(
        client = HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
        },
        url = "https://dapi.kakao.com/v2/"
    )
}

val json = Json { ignoreUnknownKeys = true }