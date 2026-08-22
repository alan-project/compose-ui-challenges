package com.example.composemarket.data.model

data class Product(
    val id: Long,
    val name: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val reviewCount: Int,
    val discountPercent: Int = 0,
    val isFavorite: Boolean = false,
    val imageUrl: String,
)
