package com.example.craterzoneassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.craterzoneassignment.data.ImageRepository
import com.example.craterzoneassignment.models.ImageResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImagesViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private var imageMutableLiveData = MutableLiveData<ImageResponse>()

    fun searchImages(query: String, page: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            var res = ImageRepository.getInstance().searchImages(query, page)

            imageMutableLiveData.postValue(res)
        }
    }

    fun getFetchedImages() : MutableLiveData<ImageResponse>{
        return imageMutableLiveData
    }
}
