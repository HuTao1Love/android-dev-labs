package ru.hutao.shop.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hutao.shop.usecases.GetProductsUseCase

class MainViewModel(private val getProductsUseCase: GetProductsUseCase) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.Loading)
    val state: StateFlow<MainState> get() = _state

    suspend fun processIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.LoadProducts -> loadProducts()
        }
    }

    private suspend fun loadProducts() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            try {
                val products = getProductsUseCase.invoke()
                _state.value = MainState.Success(products)
            } catch (e: Exception) {
                _state.value = MainState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }
}