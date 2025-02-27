package ru.hutao.shop.presentation.cartActivity

import ru.hutao.shop.data.models.CartItem

interface ICartItemChangedListener {
    fun onRemoveClicked(cartItem: CartItem)
    fun onDecreaseClicked(cartItem: CartItem)
    fun onIncreaseClicked(cartItem: CartItem)
}