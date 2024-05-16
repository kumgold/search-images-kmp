package com.example.search_images_kmp.network

import com.example.search_images_kmp.model.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.util.logging.Logger

class KakaoApi(
    private val client: HttpClient,
    private val url: String
) {
    suspend fun getImages(keyword: String): SearchResponse {
        val apiUrl = url + "search/image"

        return try {
            client.get(apiUrl) {
                headers {
                    append("Authorization", "Kakao AK ")
                }
                url {
                    parameters.append("keyword", keyword)
                }
            }.body()
        } catch (e: Exception) {
            SearchResponse(
                metaData = null,
                networkImages = emptyList()
            )
        }
    }
}