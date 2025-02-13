package ru.hutao.shop.usecases

import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.IProductRepository

class GetProductsUseCase(private val repository: IProductRepository) {
    suspend operator fun invoke(): List<Product> {
        return repository.getProducts();
    }
}