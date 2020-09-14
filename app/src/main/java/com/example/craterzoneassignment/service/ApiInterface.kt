package com.example.craterzoneassignment.service

import com.example.craterzoneassignment.models.ImageResponse
import com.example.craterzoneassignment.service.ApiEndpoint.API_KEY
import com.example.craterzoneassignment.service.ApiEndpoint.METHOD
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(ApiEndpoint.SEARCH)
    suspend fun getImages(@Query("method") method: String = METHOD,
                          @Query("api_key") apiKey: String  = API_KEY,
                          @Query("format") format: String = "json",
                          @Query("text") text: String,
                          @Query("per_page") perPage: String = "15",
                          @Query("page") page: Int = 1,
                          @Query("nojsoncallback") nojsoncallback: String = "1"): Response<ImageResponse>
}