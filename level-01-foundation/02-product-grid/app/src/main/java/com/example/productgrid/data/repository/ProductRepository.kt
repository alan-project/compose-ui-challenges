package com.example.productgrid.data.repository

import com.example.productgrid.data.model.Product
import kotlinx.coroutines.flow.StateFlow

interface ProductRepository {
  val products: StateFlow<List<Product>>

  fun toggleFavorite(productId: Long)
}
