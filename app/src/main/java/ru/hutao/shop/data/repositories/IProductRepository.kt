package ru.hutao.shop.data.repositories

import retrofit2.http.GET
import retrofit2.http.Path
import ru.hutao.shop.data.models.Product
import java.util.UUID

interface IProductRepository {
    @GET("products")
    suspend fun getProducts(): List<Product>;

    @GET("products/{id}")
    suspend fun findProductById(@Path("id") id: UUID): Product?;

    @GET("products/search/name/{partialName}")
    suspend fun searchByName(@Path("partialName") partialName: String): List<Product>;

    @GET("products/search/category/{partialName}")
    suspend fun searchByCategory(@Path("partialName") partialName: String): List<Product>;
}