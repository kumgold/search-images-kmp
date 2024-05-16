package com.example.search_images_kmp.di

import com.example.search_images_kmp.network.KakaoApi

actual class Factory {
    actual fun createApi(): KakaoApi {
        return commonCreateApi()
    }
}