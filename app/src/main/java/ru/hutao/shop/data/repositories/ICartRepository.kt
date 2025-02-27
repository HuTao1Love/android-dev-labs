package ru.hutao.shop.data.repositories;

import java.util.UUID;

import kotlin.Unit;
import ru.hutao.shop.data.models.CartItem;

interface ICartRepository {
    suspend fun addCartItem(cartItem: CartItem): Unit;

    suspend fun removeCartItem(cartItem: CartItem): Unit;

    suspend fun getCartItems(): List<CartItem>;

    suspend fun getCartItemById(id: UUID): CartItem?;

    suspend fun updateCartItemQuantity(cartItemId: UUID, quantity: Int);

    suspend fun clearCart();
}
