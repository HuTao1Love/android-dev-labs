package ru.hutao.shop.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date
import java.util.UUID

data class Product(
    val id: UUID,
    val name: String,
    val description: String,
    val manufacturer: String,
    val price: Double,
    val createdAt: Date,
    val updatedAt: Date,
    @SerializedName("uri") val image: String,
    val category: ProductCategory
) : Serializable