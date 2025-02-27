package ru.hutao.shop.presentation.productDetailActivity

import ru.hutao.shop.data.models.CartItem

sealed class ProductDetailState {
    data object Loading : ProductDetailState()
    data class Success(val item: CartItem?): ProductDetailState()
    data class Error(val message: String): ProductDetailState()
}