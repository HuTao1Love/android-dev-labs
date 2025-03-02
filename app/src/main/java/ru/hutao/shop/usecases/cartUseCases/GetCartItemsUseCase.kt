package ru.hutao.shop.usecases.cartUseCases

import android.util.Log
import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.repositories.ICartRepository

class GetCartItemsUseCase(private val deviceId: String, private val repository: ICartRepository) {
    suspend operator fun invoke(): List<CartItem> = repository.getCartItems(deviceId)
}