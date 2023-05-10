package com.example.dailynews.domain

import com.example.dailynews.data.models.Category
import com.example.dailynews.data.remote.network.NewsService
import com.example.dailynews.data.repo.NewsRepository

object NewsUseCase {
    suspend fun getNews(category: String): Category {
        return NewsRepository.getNews(category)
    }
}