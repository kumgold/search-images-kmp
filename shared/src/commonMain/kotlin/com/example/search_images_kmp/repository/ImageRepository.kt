package com.example.search_images_kmp.repository

import com.example.search_images_kmp.cache.Database
import com.example.search_images_kmp.model.NetworkImage
import com.example.searchimageskmp.LocalImage

class ImageRepository(
    private val database: Database
) {
    suspend fun getAllImages(): List<LocalImage> {
        return database.getAllImages()
    }

    fun insertImage(image: NetworkImage, keyword: String) {
        database.insertImage(image, keyword)
    }

    suspend fun deleteImages(images: List<String>) {
        database.deleteImages(images)
    }
}