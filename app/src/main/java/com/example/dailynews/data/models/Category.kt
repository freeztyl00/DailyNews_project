package com.example.dailynews.data.models

data class Category(
    val category: String,
    val data: List<News>,
    val success: Boolean
)