package ru.hutao.shop.presentation.cartActivity

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.ICartRepository
import ru.hutao.shop.usecases.cartUseCases.AddToCartUseCase
import ru.hutao.shop.usecases.cartUseCases.ClearCartUseCase
import ru.hutao.shop.usecases.cartUseCases.GetCartItemsUseCase
import ru.hutao.shop.usecases.cartUseCases.RemoveFromCartUseCase
import ru.hutao.shop.usecases.cartUseCases.UpdateCartItemQuantityUseCase
import java.util.UUID

class CartViewModel(cartRepository: ICartRepository) {
    private val _state: MutableStateFlow<CartState> = MutableStateFlow(CartState.Loading)
    val state: StateFlow<CartState> get() = _state

    private val addToCartUseCase = AddToCartUseCase(cartRepository)
    private val clearCartUseCase = ClearCartUseCase(cartRepository)
    private val getCartItemsUseCase = GetCartItemsUseCase(cartRepository)
    private val removeFromCartUseCase = RemoveFromCartUseCase(cartRepository)
    private val updateCartItemQuantityUseCase = UpdateCartItemQuantityUseCase(cartRepository)

    suspend fun processIntent(intent: CartIntent) {
        _state.value = CartState.Loading
        try {
            _state.value = when (intent) {
                is CartIntent.AddToCart -> addToCart(intent.cartItem)
                is CartIntent.ClearCart -> clearCart()
                is CartIntent.LoadCart -> getCartItems()
                is CartIntent.RemoveFromCart -> removeFromCart(intent.cartItem)
                is CartIntent.UpdateCartItemQuantity -> updateCartItemQuantity(intent.product, intent.newQuantity)
            }
        } catch (e: Exception) {
            _state.value = CartState.Error(e.message ?: "Ошибка загрузки")
        }
    }

    private suspend fun addToCart(cartItem: CartItem): CartState {
        addToCartUseCase(cartItem)
        return CartState.Success(getCartItemsUseCase())
    }

    private suspend fun clearCart(): CartState {
        clearCartUseCase()
        return CartState.Success(emptyList())
    }

    private suspend fun getCartItems(): CartState = CartState.Success(getCartItemsUseCase())

    private suspend fun removeFromCart(cartItem: CartItem): CartState {
        removeFromCartUseCase(cartItem)
        return CartState.Success(getCartItemsUseCase())
    }

    private suspend fun updateCartItemQuantity(product: Product, newQuantity: Int): CartState {
        updateCartItemQuantityUseCase(product, newQuantity)
        return CartState.Success(getCartItemsUseCase())
    }
}