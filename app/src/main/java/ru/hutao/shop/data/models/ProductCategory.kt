package ru.hutao.shop.data.models

import java.io.Serializable
import java.util.UUID

data class ProductCategory(
    val id: UUID,
    val name: String
) : Serializable