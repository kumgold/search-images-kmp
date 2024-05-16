package com.example.search_images_kmp.repository

import com.example.search_images_kmp.model.SearchResponse
import com.example.search_images_kmp.network.KakaoApi

class SearchRepository(
    private val api: KakaoApi
) {
    suspend fun searchImages(keyword: String): SearchResponse {
        return api.getImages(keyword)
    }
}