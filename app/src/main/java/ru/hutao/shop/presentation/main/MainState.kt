package ru.hutao.shop.presentation.main

import ru.hutao.shop.data.models.Product

sealed class MainState {
    data object Loading : MainState()
    data class Success(val products: List<Product>) : MainState()
    data class Error(val message: String) : MainState()
}
