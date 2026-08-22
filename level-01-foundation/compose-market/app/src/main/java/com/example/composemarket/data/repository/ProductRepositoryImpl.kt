package com.example.composemarket.data.repository

import com.example.composemarket.data.mock.MockProducts
import com.example.composemarket.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductRepositoryImpl : ProductRepository {
    private val _products = MutableStateFlow(MockProducts.items)

    override val products: Flow<List<Product>> = _products.asStateFlow()

    override fun toggleFavorite(productId: Long) {
        _products.update { products ->
            products.map { product ->
                if (product.id == productId) {
                    product.copy(isFavorite = !product.isFavorite)
                } else {
                    product
                }
            }
        }
    }
}
