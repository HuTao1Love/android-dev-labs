package ru.hutao.shop.usecases.productsUseCases

import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.IProductRepository

class SearchProductsUseCase(private val repository: IProductRepository, private val partialName: String): IGetProductsUseCase {
    override suspend operator fun invoke(): List<Product> = repository.searchByName(partialName)
}