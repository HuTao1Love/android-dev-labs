package ru.hutao.shop.usecases

import ru.hutao.shop.data.models.Product

public interface IGetProductsUseCase {
    suspend operator fun invoke(): List<Product>;
}