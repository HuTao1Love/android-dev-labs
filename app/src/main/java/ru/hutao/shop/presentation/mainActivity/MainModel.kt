package ru.hutao.shop.presentation.mainActivity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.hutao.shop.data.repositories.IProductRepository
import ru.hutao.shop.usecases.productsUseCases.CategorySearchUseCase
import ru.hutao.shop.usecases.productsUseCases.GetProductsUseCase
import ru.hutao.shop.usecases.productsUseCases.IGetProductsUseCase
import ru.hutao.shop.usecases.productsUseCases.SearchProductsUseCase

class MainModel(private val productRepository: IProductRepository) : ViewModel() {
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
        _state.value = MainState.Loading
        try {
            _state.value = MainState.Success(useCase.invoke())
        } catch (e: Exception) {
            _state.value = MainState.Error(e.message ?: "Ошибка загрузки")
        }
    }
}