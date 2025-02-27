package ru.hutao.shop.usecases.cartUseCases

import android.util.Log
import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.ICartRepository
import java.util.UUID

class UpdateCartItemQuantityUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(product: Product, quantity: Int): Unit {
        if (repository.getCartItemById(product.id) != null) {
            repository.updateCartItemQuantity(product.id, quantity)
        } else {
            repository.addCartItem(CartItem(product, quantity))
        }
    }
}