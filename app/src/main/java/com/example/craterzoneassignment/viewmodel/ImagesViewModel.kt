package com.example.craterzoneassignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.craterzoneassignment.data.FetchImageRepository
import com.example.craterzoneassignment.data.Resource
import com.example.craterzoneassignment.models.Photo
import java.util.*

class ImagesViewModel(private val imageRepository: FetchImageRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()

    val query : LiveData<String> = _query

    val results: LiveData<Resource<List<Photo>>> = _query.switchMap { search ->
        if (search.isBlank()) {
            AbsentLiveData.create()
        } else {
            imageRepository.searchImages(_query.value!!, 1)
        }
    }

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == _query.value) {
            return
        }
        _query.value = input
    }

}
