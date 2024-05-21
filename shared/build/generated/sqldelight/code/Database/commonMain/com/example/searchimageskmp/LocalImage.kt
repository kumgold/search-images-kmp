package com.example.searchimageskmp

import kotlin.String

public data class LocalImage(
  public val id: String,
  public val imageUrl: String,
  public val thumbnailUrl: String?,
  public val documentUrl: String?,
  public val keyword: String?,
)
