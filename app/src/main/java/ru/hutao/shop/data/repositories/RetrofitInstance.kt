package ru.hutao.shop.data.repositories

import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotFoundInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return if (response.code() == 404) {
            Response.Builder()
                .request(response.request())
                .protocol(response.protocol())
                .code(200)
                .message("Not Found")
                .body(ResponseBody.create(null, "null"))
                .build()
        } else {
            response
        }
    }
}
object RetrofitInstance {
    private const val URL: String = "http://185.193.102.149:81/";

    private val retrofit: Retrofit by lazy {
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, JsonSerializer<Date> { src, _, _ ->
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                JsonPrimitive(formatter.format(src))
            })
            .create()

        val client = OkHttpClient.Builder()
            .addInterceptor(NotFoundInterceptor())
            .build();

        Retrofit.Builder()
            .baseUrl(URL)
            .client(client)
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