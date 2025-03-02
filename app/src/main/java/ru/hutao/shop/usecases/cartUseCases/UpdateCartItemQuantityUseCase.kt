package ru.hutao.shop.usecases.cartUseCases

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.ICartRepository
import ru.hutao.shop.data.repositories.getCartItemById
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UpdateCartItemQuantityUseCase(private val deviceId: String, private val repository: ICartRepository) {
    suspend operator fun invoke(product: Product, quantity: Int): Unit {
        if (repository.getCartItemById(product.id, deviceId) != null) {
            repository.updateCartItemQuantity(product.id, deviceId, quantity)
        } else {
            if (quantity < 0) {
                return
            }

            val gson = GsonBuilder()
                .registerTypeAdapter(Date::class.java, JsonSerializer<Date> { src, _, _ ->
                    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                    JsonPrimitive(formatter.format(src))
                })
                .create()
            Log.e("HERE", gson.toJson(CartItem(product, quantity)))
            repository.addCartItem(deviceId, CartItem(product, quantity))
        }
    }
}