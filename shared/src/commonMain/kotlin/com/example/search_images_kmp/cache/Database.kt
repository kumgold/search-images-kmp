package com.example.search_images_kmp.cache

import com.example.search_images_kmp.Database
import com.example.searchimageskmp.LocalImage

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun getAllImages(): List<LocalImage> {
        return dbQuery.selectAllImages().executeAsList()
    }
}