package com.example.productdetail.data.repository

import com.example.productdetail.data.mock.MockProduct
import com.example.productdetail.data.model.ProductColor
import com.example.productdetail.data.model.ProductDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryProductDetailRepository : ProductDetailRepository {
  private val _detail = MutableStateFlow(MockProduct.detail)

  override val detail: StateFlow<ProductDetail> = _detail.asStateFlow()

  override fun selectColor(color: ProductColor) {
    _detail.update { it.copy(selectedColor = color) }
  }

  override fun selectSize(size: Int) {
    if (size in _detail.value.product.sizes) {
      _detail.update { it.copy(selectedSize = size) }
    }
  }

  override fun increaseQuantity() {
    _detail.update { it.copy(quantity = (it.quantity + 1).coerceAtMost(10)) }
  }

  override fun decreaseQuantity() {
    _detail.update { it.copy(quantity = (it.quantity - 1).coerceAtLeast(1)) }
  }

  override fun toggleFavorite() {
    _detail.update { it.copy(isFavorite = !it.isFavorite) }
  }

  override fun addToCart() {
    _detail.update { it.copy(cartItemCount = it.cartItemCount + it.quantity) }
  }
}
