package com.example.craterzoneassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.craterzoneassignment.data.ImageRepository

class ImagesViewModelFactory (
    private val imageRepository: ImageRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImagesViewModel(imageRepository) as T
    }
}