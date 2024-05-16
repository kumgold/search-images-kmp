package com.example.search_images_kmp.di

import com.example.search_images_kmp.repository.SearchRepository

class AppContainer(
    private val factory: Factory
) {
    val repository: SearchRepository by lazy {
        SearchRepository(factory.createApi())
    }
}