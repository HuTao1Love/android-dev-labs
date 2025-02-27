package ru.hutao.shop.presentation.productDetailActivity

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

class ProductDetailViewModel(cartRepository: ICartRepository) {
    private val _state: MutableStateFlow<ProductDetailState> = MutableStateFlow(ProductDetailState.Loading)
    val state: StateFlow<ProductDetailState> get() = _state

    var quantity: Int? = null

    private val addToCartUseCase = AddToCartUseCase(cartRepository)
    private val getCartItemByIdUseCase = GetCartItemByIdUseCase(cartRepository)
    private val removeFromCartUseCase = RemoveFromCartUseCase(cartRepository)
    private val updateCartItemQuantityUseCase = UpdateCartItemQuantityUseCase(cartRepository)

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
        val data = getCartItemByIdUseCase(cartItem.product.id)
        quantity = data?.quantity
        return ProductDetailState.Success(data)
    }

    private suspend fun removeFromCart(cartItem: CartItem): ProductDetailState {
        removeFromCartUseCase(cartItem)
        val data = getCartItemByIdUseCase(cartItem.product.id)
        quantity = data?.quantity
        return ProductDetailState.Success(data)
    }

    private suspend fun updateCartItemQuantity(product: Product, newQuantity: Int): ProductDetailState {
        updateCartItemQuantityUseCase(product, newQuantity)
        val data = getCartItemByIdUseCase(product.id)
        quantity = data?.quantity
        return ProductDetailState.Success(data)
    }

    private suspend fun getById(productId: UUID): ProductDetailState {
        val data = getCartItemByIdUseCase(productId)
        quantity = data?.quantity
        return ProductDetailState.Success(data)
    }
}