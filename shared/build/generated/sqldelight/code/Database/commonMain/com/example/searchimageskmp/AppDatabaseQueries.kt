package com.example.searchimageskmp

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.String
import kotlin.collections.Collection

public class AppDatabaseQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAllImages(mapper: (
    id: String,
    imageUrl: String,
    thumbnailUrl: String?,
    documentUrl: String?,
    keyword: String?,
  ) -> T): Query<T> = Query(-1_354_787_536, arrayOf("LocalImage"), driver, "AppDatabase.sq",
      "selectAllImages",
      "SELECT LocalImage.id, LocalImage.imageUrl, LocalImage.thumbnailUrl, LocalImage.documentUrl, LocalImage.keyword FROM LocalImage") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2),
      cursor.getString(3),
      cursor.getString(4)
    )
  }

  public fun selectAllImages(): Query<LocalImage> = selectAllImages { id, imageUrl, thumbnailUrl,
      documentUrl, keyword ->
    LocalImage(
      id,
      imageUrl,
      thumbnailUrl,
      documentUrl,
      keyword
    )
  }

  public fun <T : Any> getImage(id: String, mapper: (
    id: String,
    imageUrl: String,
    thumbnailUrl: String?,
    documentUrl: String?,
    keyword: String?,
  ) -> T): Query<T> = GetImageQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2),
      cursor.getString(3),
      cursor.getString(4)
    )
  }

  public fun getImage(id: String): Query<LocalImage> = getImage(id) { id_, imageUrl, thumbnailUrl,
      documentUrl, keyword ->
    LocalImage(
      id_,
      imageUrl,
      thumbnailUrl,
      documentUrl,
      keyword
    )
  }

  public fun insertImage(
    id: String,
    imageUrl: String,
    thumbnailUrl: String?,
    documentUrl: String?,
    keyword: String?,
  ) {
    driver.execute(-830_552_331, """
        |INSERT OR REPLACE INTO LocalImage(id, imageUrl, thumbnailUrl, documentUrl, keyword)
        |VALUES (?, ?, ?, ?, ?)
        """.trimMargin(), 5) {
          bindString(0, id)
          bindString(1, imageUrl)
          bindString(2, thumbnailUrl)
          bindString(3, documentUrl)
          bindString(4, keyword)
        }
    notifyQueries(-830_552_331) { emit ->
      emit("LocalImage")
    }
  }

  public fun deleteImages(id: Collection<String>) {
    val idIndexes = createArguments(count = id.size)
    driver.execute(null, """DELETE FROM LocalImage WHERE id IN $idIndexes""", id.size) {
          id.forEachIndexed { index, id_ ->
            bindString(index, id_)
          }
        }
    notifyQueries(-1_491_444_336) { emit ->
      emit("LocalImage")
    }
  }

  private inner class GetImageQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("LocalImage", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("LocalImage", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(1_595_257_778,
        """SELECT LocalImage.id, LocalImage.imageUrl, LocalImage.thumbnailUrl, LocalImage.documentUrl, LocalImage.keyword FROM LocalImage WHERE id = ?""",
        mapper, 1) {
      bindString(0, id)
    }

    override fun toString(): String = "AppDatabase.sq:getImage"
  }
}
