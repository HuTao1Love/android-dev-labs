package ru.hutao.shop.usecases.cartUseCases

import ru.hutao.shop.data.repositories.ICartRepository

class ClearCartUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(): Unit = repository.clearCart()
}