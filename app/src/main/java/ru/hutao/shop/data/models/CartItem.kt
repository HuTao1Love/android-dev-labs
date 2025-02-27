package ru.hutao.shop.data.models

import java.io.Serializable

data class CartItem(
    val product: Product,
    var quantity: Int
) : Serializable;