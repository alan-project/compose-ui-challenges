package com.example.composemarket.data.repository

import com.example.composemarket.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    val products: Flow<List<Product>>

    fun toggleFavorite(productId: Long)
}
