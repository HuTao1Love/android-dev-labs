package ru.hutao.shop.data.repositories

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.hutao.shop.data.models.CartItem
import java.util.UUID

interface ICartRepository {
    @POST("cart")
    suspend fun addCartItem(@Query("token") deviceId: String, @Body cartItem: CartItem);

    @DELETE("cart")
    suspend fun removeCartItem(@Query("token") deviceId: String, @Body cartItem: CartItem);

    @GET("cart")
    suspend fun getCartItems(@Query("token") deviceId: String): List<CartItem>;

    @GET("cart/{id}")
    suspend fun getCartItemByIdInternal(@Path("id") id: UUID, @Query("token") deviceId: String): Response<CartItem?>;

    @PATCH("cart/{id}")
    suspend fun updateCartItemQuantity(@Path("id") cartItemId: UUID, @Query("token") deviceId: String, @Query("quantity") quantity: Int);

    @DELETE("cart/clear")
    suspend fun clearCart(@Query("token") deviceId: String);
}

suspend fun ICartRepository.getCartItemById(id: UUID, deviceId: String): CartItem? {
    return try {
        this.getCartItemByIdInternal(id, deviceId).body()
    } catch (e: Exception) {
        if (e is retrofit2.HttpException && e.code() == 404) {
            null
        } else {
            throw e
        }
    }
}

