package ru.hutao.shop.presentation.usecases.cartUseCases

import ru.hutao.shop.data.repositories.ICartRepository

class ClearCartUseCase(private val deviceId: String, private val repository: ICartRepository) {
    suspend operator fun invoke(): Unit = repository.clearCart(deviceId)
}