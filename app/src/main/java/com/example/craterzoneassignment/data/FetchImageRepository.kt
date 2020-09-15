package com.example.craterzoneassignment.data

import RepoSearchResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.craterzoneassignment.MyApplication
import com.example.craterzoneassignment.db.ImageDao
import com.example.craterzoneassignment.db.ImageDb
import com.example.craterzoneassignment.models.ImageResponse
import com.example.craterzoneassignment.models.Photo
import com.example.craterzoneassignment.service.ApiInterface
import com.example.craterzoneassignment.utils.AppExecutors
import com.example.craterzoneassignment.utils.NetworkBoundResource

class FetchImageRepository constructor (val imageDao: ImageDao,
                                       val apiInterface: ApiInterface,
                                       val imageDb: ImageDb,
                                       val appExecutors: AppExecutors) {

    fun searchImages(query: String, page: Int): LiveData<Resource<List<Photo>>> {

        return object : NetworkBoundResource<List<Photo>, ImageResponse>(appExecutors) {

            override fun saveCallResult(item: ImageResponse) {
                val repoSearchResult = RepoSearchResult(
                    query = query,
                    image = item.photos.photo)

                imageDb.beginTransaction()

                try {
                    imageDao.insert(repoSearchResult)
                    imageDb.setTransactionSuccessful()
                } finally {
                    imageDb.endTransaction()
                }
            }

            override fun shouldFetch(data: List<Photo>?) = MyApplication.isNetworkAvailable()

            override fun loadFromDb(): LiveData<List<Photo>> {
                return Transformations.map(imageDao.search(query)) {
                    it?.image
                }
            }

            override fun createCall() = apiInterface.getImages(text = query, page = page)

            override fun processResponse(response: ApiSuccessResponse<ImageResponse>): ImageResponse {
                val body = response.body
                return body
            }
        }.asLiveData()
    }
}
