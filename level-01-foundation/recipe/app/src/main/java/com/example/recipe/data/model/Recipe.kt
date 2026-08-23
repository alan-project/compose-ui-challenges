package com.example.recipe.data.model

data class Recipe(
    val id: Long,
    val name: String,
    val category: String,
    val durationMinutes: Int,
    val rating: Double,
    val calories: Int,
    val imageUrl: String,
    val isFavorite: Boolean = false,
)
