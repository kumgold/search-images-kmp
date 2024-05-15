package com.example.search_images_kmp.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkImage(
    val collection: String? = null,
    val thumbnail_url: String? = null,
    val image_url: String,
    val width: Int,
    val height: Int,
    val display_sitename: String? = null,
    val doc_url: String? = null,
    val datetime: String = ""
)