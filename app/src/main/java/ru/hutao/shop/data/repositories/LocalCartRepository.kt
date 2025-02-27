package ru.hutao.shop.data.repositories

import ru.hutao.shop.data.models.CartItem
import java.util.UUID

object LocalCartRepository: ICartRepository {
    private val cartItems = mutableMapOf<UUID, CartItem>()

    override suspend fun addCartItem(cartItem: CartItem) {
        cartItems[cartItem.product.id] = cartItem
    }

    override suspend fun removeCartItem(cartItem: CartItem) {
        cartItems.remove(cartItem.product.id)
    }

    override suspend fun getCartItems(): List<CartItem> = cartItems.values.toList()

    override suspend fun getCartItemById(id: UUID): CartItem? = cartItems[id]

    override suspend fun updateCartItemQuantity(cartItemId: UUID, quantity: Int) {
        val item: CartItem? = cartItems[cartItemId]
        when {
            item == null || quantity < 0 -> {}
            quantity == 0 -> cartItems.remove(item.product.id)
            else -> item.quantity = quantity
        }
    }

    override suspend fun clearCart() = cartItems.clear()
}