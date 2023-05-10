package com.example.dailynews.data.local

object NewsCategories {
    private val categories = listOf(
        "technology",
        "world",
        "sports",
        "automobile",
        "business",
        "science",
        "politics",
        "startup",
        "hatke",)

    fun getCategories(): List<String>{
        return categories
    }
}