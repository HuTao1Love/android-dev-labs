package ru.hutao.shop.usecases

import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.IProductRepository

class SearchProductsUseCase(private val repository: IProductRepository) {
    suspend operator fun invoke(partialName: String): List<Product> = repository.searchProducts(partialName)
}