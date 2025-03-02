package ru.hutao.shop.usecases.cartUseCases

import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.repositories.ICartRepository

class RemoveFromCartUseCase(private val deviceId: String, private val repository: ICartRepository) {
    suspend operator fun invoke(cartItem: CartItem): Unit = repository.removeCartItem(deviceId, cartItem)
}