package ru.hutao.shop.usecases.cartUseCases

import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.repositories.ICartRepository

class AddToCartUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(cartItem: CartItem): Unit = repository.addCartItem(cartItem)
}