package com.example.productdetail.data.model

enum class ProductColor(val label: String) {
  MIDNIGHT("Midnight"),
  CORAL("Coral"),
  CLOUD("Cloud"),
}

data class Product(
  val id: Long,
  val name: String,
  val category: String,
  val description: String,
  val price: Double,
  val rating: Double,
  val reviewCount: Int,
  val colors: List<ProductColor>,
  val sizes: List<Int>,
)

data class ProductDetail(
  val product: Product,
  val selectedColor: ProductColor,
  val selectedSize: Int,
  val quantity: Int = 1,
  val isFavorite: Boolean = false,
  val cartItemCount: Int = 0,
)
