package com.example.search_images_kmp.cache

import com.example.search_images_kmp.model.NetworkImage
import com.example.searchimageskmp.LocalImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    suspend fun getAllImages(): List<LocalImage> = withContext(Dispatchers.IO) {
        dbQuery.selectAllImages().executeAsList()
    }

    fun insertImage(image: NetworkImage, keyword: String) {
        dbQuery.insertImage(
            id = image.imageUrl + image.documentUrl,
            imageUrl = image.imageUrl,
            thumbnailUrl = image.thumbnailUrl,
            documentUrl = image.documentUrl,
            keyword = keyword
        )
    }

    suspend fun deleteImages(images: List<String>) {
        withContext(Dispatchers.IO) {
            dbQuery.deleteImages(images)
        }
    }
}