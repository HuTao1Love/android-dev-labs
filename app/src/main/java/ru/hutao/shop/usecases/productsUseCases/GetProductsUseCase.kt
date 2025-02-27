package ru.hutao.shop.usecases.productsUseCases

import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.IProductRepository

class GetProductsUseCase(private val repository: IProductRepository): IGetProductsUseCase {
    override suspend operator fun invoke(): List<Product> = repository.getProducts()
}