package com.example.productgrid.data.repository

import com.example.productgrid.data.mock.MockProducts
import com.example.productgrid.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryProductRepository : ProductRepository {
  private val _products = MutableStateFlow(MockProducts.items)

  override val products: StateFlow<List<Product>> = _products.asStateFlow()

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
