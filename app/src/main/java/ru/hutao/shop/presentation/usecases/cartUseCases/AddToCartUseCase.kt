package ru.hutao.shop.presentation.usecases.cartUseCases

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.repositories.ICartRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddToCartUseCase(private val deviceId: String, private val repository: ICartRepository) {
    suspend operator fun invoke(cartItem: CartItem) = repository.addCartItem(deviceId, cartItem)
}