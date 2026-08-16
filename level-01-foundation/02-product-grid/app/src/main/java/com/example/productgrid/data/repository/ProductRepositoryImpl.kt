package com.example.productgrid.data.repository

import com.example.productgrid.data.mock.MockProducts
import com.example.productgrid.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Mock repository backed by in-memory data for this Compose exercise.
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
