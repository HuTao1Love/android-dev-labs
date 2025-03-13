package ru.hutao.shop.presentation.usecases.cartUseCases

import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.repositories.ICartRepository
import ru.hutao.shop.data.repositories.getCartItemById
import java.util.UUID

class GetCartItemByIdUseCase(private val deviceId: String, private val repository: ICartRepository) {
    suspend operator fun invoke(id: UUID): CartItem? = repository.getCartItemById(id, deviceId)
}