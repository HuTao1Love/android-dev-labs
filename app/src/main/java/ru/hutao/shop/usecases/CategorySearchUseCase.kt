package ru.hutao.shop.usecases

import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.IProductRepository

class CategorySearchUseCase(private val repository: IProductRepository, private val category: String): IGetProductsUseCase {
    override suspend operator fun invoke(): List<Product> =  repository.searchByCategory(category);
}