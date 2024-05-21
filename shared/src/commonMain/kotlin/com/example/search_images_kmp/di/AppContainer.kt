package com.example.search_images_kmp.di

import com.example.search_images_kmp.repository.ImageRepository
import com.example.search_images_kmp.repository.SearchRepository

class AppContainer(
    private val factory: Factory
) {
    val searchRepository: SearchRepository by lazy {
        SearchRepository(factory.createApi())
    }
    val imageRepository: ImageRepository by lazy {
        ImageRepository(factory.createDatabase())
    }
}