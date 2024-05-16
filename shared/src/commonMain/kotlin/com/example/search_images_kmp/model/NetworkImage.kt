package com.example.search_images_kmp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkImage(
    @SerialName("collection") val collection: String? = null,
    @SerialName("thumbnail_url") val thumbnailUrl: String? = null,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("display_sitename") val displaySiteName: String? = null,
    @SerialName("doc_url") val documentUrl: String? = null,
    @SerialName("datetime") val datetime: String = ""
)