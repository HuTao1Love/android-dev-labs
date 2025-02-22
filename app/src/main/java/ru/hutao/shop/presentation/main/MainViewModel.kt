package ru.hutao.shop.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hutao.shop.data.repositories.IProductRepository
import ru.hutao.shop.usecases.CategorySearchUseCase
import ru.hutao.shop.usecases.GetProductsUseCase
import ru.hutao.shop.usecases.IGetProductsUseCase
import ru.hutao.shop.usecases.SearchProductsUseCase

class MainViewModel(private val productRepository: IProductRepository) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.Loading)
    val state: StateFlow<MainState> get() = _state

    suspend fun processIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.LoadProducts -> load(GetProductsUseCase(productRepository))
            is MainIntent.SearchProducts -> load(SearchProductsUseCase(productRepository, intent.partialText))
            is MainIntent.SearchProductsCategory -> load(CategorySearchUseCase(productRepository, intent.category))
        }
    }

    private suspend fun load(useCase: IGetProductsUseCase) {
        viewModelScope.launch {
            _state.value = MainState.Loading
            try {
                val products = useCase.invoke()
                _state.value = MainState.Success(products)
            } catch (e: Exception) {
                _state.value = MainState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }
}