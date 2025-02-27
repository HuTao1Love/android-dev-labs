package ru.hutao.shop.presentation.productDetailActivity

import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.models.Product
import java.util.UUID

sealed class ProductDetailIntent {
    data class LoadProduct(val productId: UUID) : ProductDetailIntent()
    data class AddToCart(val cartItem: CartItem): ProductDetailIntent()
    data class RemoveFromCart(val cartItem: CartItem): ProductDetailIntent()
    data class UpdateCartItemQuantity(val product: Product, val newQuantity: Int): ProductDetailIntent()
}