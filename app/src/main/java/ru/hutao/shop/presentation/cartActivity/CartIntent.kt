package ru.hutao.shop.presentation.cartActivity

import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.models.Product
import java.util.UUID

sealed class CartIntent {
    data object LoadCart: CartIntent()
    data object ClearCart: CartIntent()
    data class AddToCart(val cartItem: CartItem): CartIntent()
    data class RemoveFromCart(val cartItem: CartItem): CartIntent()
    data class UpdateCartItemQuantity(val product: Product, val newQuantity: Int): CartIntent()
}