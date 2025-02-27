package ru.hutao.shop.presentation.cartActivity

import ru.hutao.shop.data.models.CartItem

sealed class CartState {
    data object Loading : CartState()
    data class Success(val items: List<CartItem>): CartState()
    data class Error(val message: String): CartState()
}