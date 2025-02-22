package ru.hutao.shop.data.repositories

import ru.hutao.shop.data.models.Product
import java.util.UUID

class ProductRepository: IProductRepository {

    override suspend fun getProducts(): List<Product> {
        return RetrofitInstance.productRepository.getProducts()
    }

    override suspend fun findProductById(id: UUID): Product? {
        return RetrofitInstance.productRepository.findProductById(id)
    }

    override suspend fun searchByName(partialName: String): List<Product> {
        return RetrofitInstance.productRepository.searchByName(partialName)
    }

    override suspend fun searchByCategory(partialName: String): List<Product> {
        return RetrofitInstance.productRepository.searchByCategory(partialName)
    }
}