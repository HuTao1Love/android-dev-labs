package ru.hutao.shop.data.repositories

import ru.hutao.shop.data.models.Product
import java.util.UUID

interface IProductRepository {
    suspend fun getProducts(): List<Product>;
    suspend fun searchProducts(partialName: String): List<Product>;
    suspend fun findProductById(id: UUID): Product?;
}