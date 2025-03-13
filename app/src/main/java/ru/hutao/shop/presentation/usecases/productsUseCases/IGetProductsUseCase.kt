package ru.hutao.shop.presentation.usecases.productsUseCases

import ru.hutao.shop.data.models.Product

public interface IGetProductsUseCase {
    suspend operator fun invoke(): List<Product>;
}