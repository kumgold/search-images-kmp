package com.example.search_images_kmp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("meta") val metaData: MetaData? = null,
    @SerialName("documents") val images: List<NetworkImage> = emptyList(),
    val message: String? = null
)