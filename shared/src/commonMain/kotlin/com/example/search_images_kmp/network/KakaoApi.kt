package com.example.search_images_kmp.network

import com.example.search_images_kmp.model.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers

class KakaoApi(
    private val client: HttpClient,
    private val url: String
) {
    suspend fun getImages(keyword: String, page: Int): SearchResponse {
        val apiUrl = url + "search/image"

        return try {
            client.get(apiUrl) {
                headers {
                    append("Authorization", "KakaoAK 4540175c9b32da01969fa59187449f41")
                }
                url {
                    parameters.append("query", keyword)
                    parameters.append("page", page.toString())
                }
            }.body()
        } catch (e: Exception) {
            SearchResponse(
                metaData = null,
                images = emptyList(),
                message = e.cause.toString() + " message :" + e.message
            )
        }
    }
}