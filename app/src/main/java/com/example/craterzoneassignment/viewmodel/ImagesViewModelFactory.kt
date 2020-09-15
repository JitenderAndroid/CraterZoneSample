package com.example.craterzoneassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.craterzoneassignment.data.FetchImageRepository

class ImagesViewModelFactory (
    private val imageRepository: FetchImageRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImagesViewModel(imageRepository) as T
    }
}