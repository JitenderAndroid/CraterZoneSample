package com.example.craterzoneassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.craterzoneassignment.data.FetchImageRepository
import com.example.craterzoneassignment.data.Resource
import com.example.craterzoneassignment.models.ImageResponse
import com.example.craterzoneassignment.models.Photo

class ImagesViewModel(private val imageRepository: FetchImageRepository) : ViewModel() {

    //private var imageMutableLiveData = MutableLiveData<ImageResponse>()

    lateinit var results: LiveData<Resource<List<Photo>>>

    fun searchImages(query: String, page: Int) {
        results = imageRepository.searchImages(query, page)
    }
}
