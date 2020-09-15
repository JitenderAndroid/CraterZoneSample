package com.example.craterzoneassignment.data

import com.example.craterzoneassignment.models.ImageResponse
import com.example.craterzoneassignment.service.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepository {

    @Throws(IllegalStateException::class)
    public suspend fun searchImages(query: String, page: Int): ImageResponse? {
        val response = RetrofitClient.apiService.getImages(text = query, page = page)

        return null
    }

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: ImageRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ImageRepository()
            }
    }
}