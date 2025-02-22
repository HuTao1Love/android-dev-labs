package ru.hutao.shop.data.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val URL: String = "http://194.58.59.78:80/";

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productRepository: IProductRepository by lazy {
        retrofit.create(IProductRepository::class.java)
    }
}