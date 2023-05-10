package com.example.dailynews.data.remote.network

import com.example.dailynews.data.remote.network.interfaces.NewsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsService {
    private const val BASE_URL = "https://inshorts.deta.dev/"
    val newsApi: NewsApi = createRetrofit().create(NewsApi::class.java)

    private fun createRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}