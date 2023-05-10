package com.example.dailynews.data.repo

import com.example.dailynews.domain.NewsUseCase
import com.example.dailynews.data.models.Category
import com.example.dailynews.data.remote.network.NewsService

object NewsRepository {
    val api = NewsService.newsApi
    suspend fun getNews(category: String): Category {
        return api.getNews(category)
    }
}