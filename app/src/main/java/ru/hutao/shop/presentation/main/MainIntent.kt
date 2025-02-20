package ru.hutao.shop.presentation.main

sealed class MainIntent {
    data object LoadProducts : MainIntent()
    data class SearchProducts(val partialText: String) : MainIntent()
}