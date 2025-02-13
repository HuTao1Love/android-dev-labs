package ru.hutao.shop.data.repositories

import ru.hutao.shop.data.models.Product
import java.util.UUID

class ProductRepository: IProductRepository {
    private var products: List<Product> = List(3) {
        Product(UUID.randomUUID(), "Кепка", 50.0, "https://example.com/pic1.png")
        Product(UUID.randomUUID(), "Брелок", 20.0, "https://example.com/pic2.png")
        Product(UUID.randomUUID(), "Футболка", 80.0, "https://example.com/pic3.png")
    };

    override suspend fun getProducts(): List<Product> = products;

    override suspend fun findProductById(id: UUID): Product? = products.firstOrNull { it.id == id };
}