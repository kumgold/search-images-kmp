package com.example.search_images_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform