package com.example.dailynews.data.remote.network.interfaces

import com.example.dailynews.data.models.Category
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("news")
    suspend fun getNews(@Query("category") category: String): Category
}