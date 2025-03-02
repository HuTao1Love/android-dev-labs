package ru.hutao.shop.data.repositories

import android.text.format.DateFormat
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object RetrofitInstance {
    private const val URL: String = "http://194.58.59.78:80/";

    private val retrofit: Retrofit by lazy {
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, JsonSerializer<Date> { src, _, _ ->
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                JsonPrimitive(formatter.format(src))
            })
            .create()

        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val productRepository: IProductRepository by lazy {
        retrofit.create(IProductRepository::class.java)
    }

    val cartRepository: ICartRepository by lazy {
        retrofit.create(ICartRepository::class.java)
    }
}