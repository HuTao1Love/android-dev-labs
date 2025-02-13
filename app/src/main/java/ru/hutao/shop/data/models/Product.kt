package ru.hutao.shop.data.models

import java.util.UUID

data class Product(
    var id: UUID,
    val name: String,
    val price: Double,
    val image: String
)