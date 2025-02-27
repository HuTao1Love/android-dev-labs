package ru.hutao.shop.usecases.cartUseCases

import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.repositories.ICartRepository
import java.util.UUID

class GetCartItemByIdUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(id: UUID): CartItem? = repository.getCartItemById(id)
}