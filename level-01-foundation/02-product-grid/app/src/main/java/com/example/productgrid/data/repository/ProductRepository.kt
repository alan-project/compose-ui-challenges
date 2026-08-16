package com.example.productgrid.data.repository

import com.example.productgrid.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    val products: Flow<List<Product>>

    fun toggleFavorite(productId: Long)
}
