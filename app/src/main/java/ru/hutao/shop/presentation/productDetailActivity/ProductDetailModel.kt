package ru.hutao.shop.presentation.productDetailActivity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.models.Product
import ru.hutao.shop.data.repositories.ICartRepository
import ru.hutao.shop.usecases.cartUseCases.AddToCartUseCase
import ru.hutao.shop.usecases.cartUseCases.GetCartItemByIdUseCase
import ru.hutao.shop.usecases.cartUseCases.RemoveFromCartUseCase
import ru.hutao.shop.usecases.cartUseCases.UpdateCartItemQuantityUseCase
import java.util.UUID

class ProductDetailModel(deviceId: String, cartRepository: ICartRepository) : ViewModel() {
    private val _state: MutableStateFlow<ProductDetailState> = MutableStateFlow(ProductDetailState.Loading)
    val state: StateFlow<ProductDetailState> get() = _state

    private val addToCartUseCase = AddToCartUseCase(deviceId, cartRepository)
    private val getCartItemByIdUseCase = GetCartItemByIdUseCase(deviceId, cartRepository)
    private val removeFromCartUseCase = RemoveFromCartUseCase(deviceId, cartRepository)
    private val updateCartItemQuantityUseCase = UpdateCartItemQuantityUseCase(deviceId, cartRepository)

    suspend fun processIntent(intent: ProductDetailIntent) {
        _state.value = ProductDetailState.Loading

        try {
            _state.value = when (intent) {
                is ProductDetailIntent.LoadProduct -> getById(intent.productId)
                is ProductDetailIntent.AddToCart -> addToCart(intent.cartItem)
                is ProductDetailIntent.RemoveFromCart -> removeFromCart(intent.cartItem)
                is ProductDetailIntent.UpdateCartItemQuantity -> updateCartItemQuantity(intent.product, intent.newQuantity)
            }
        } catch (e: Exception) {
            _state.value = ProductDetailState.Error(e.message ?: "Ошибка загрузки")
        }
    }

    private suspend fun addToCart(cartItem: CartItem): ProductDetailState {
        addToCartUseCase(cartItem)
        return ProductDetailState.Success(getCartItemByIdUseCase(cartItem.product.id))
    }

    private suspend fun removeFromCart(cartItem: CartItem): ProductDetailState {
        removeFromCartUseCase(cartItem)
        return ProductDetailState.Success(getCartItemByIdUseCase(cartItem.product.id))
    }

    private suspend fun updateCartItemQuantity(product: Product, newQuantity: Int): ProductDetailState {
        updateCartItemQuantityUseCase(product, newQuantity)
        return ProductDetailState.Success(getCartItemByIdUseCase(product.id))
    }

    private suspend fun getById(productId: UUID): ProductDetailState {
        return ProductDetailState.Success(getCartItemByIdUseCase(productId))
    }
}