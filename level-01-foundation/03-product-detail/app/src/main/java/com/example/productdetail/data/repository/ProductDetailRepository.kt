package com.example.productdetail.data.repository

import com.example.productdetail.data.model.ProductColor
import com.example.productdetail.data.model.ProductDetail
import kotlinx.coroutines.flow.StateFlow

interface ProductDetailRepository {
  val detail: StateFlow<ProductDetail>

  fun selectColor(color: ProductColor)

  fun selectSize(size: Int)

  fun increaseQuantity()

  fun decreaseQuantity()

  fun toggleFavorite()

  fun addToCart()
}
