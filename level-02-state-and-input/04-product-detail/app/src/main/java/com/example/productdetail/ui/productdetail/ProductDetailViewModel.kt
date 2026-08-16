package com.example.productdetail.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productdetail.data.model.ProductColor
import com.example.productdetail.data.model.ProductDetail
import com.example.productdetail.data.repository.ProductDetailRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ProductDetailUiState(val detail: ProductDetail)

class ProductDetailViewModel(
  private val productDetailRepository: ProductDetailRepository,
) : ViewModel() {
  val uiState: StateFlow<ProductDetailUiState> =
    productDetailRepository.detail
      .map(::ProductDetailUiState)
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ProductDetailUiState(productDetailRepository.detail.value),
      )

  fun onColorSelected(color: ProductColor) = productDetailRepository.selectColor(color)

  fun onSizeSelected(size: Int) = productDetailRepository.selectSize(size)

  fun onIncreaseQuantity() = productDetailRepository.increaseQuantity()

  fun onDecreaseQuantity() = productDetailRepository.decreaseQuantity()

  fun onFavoriteClick() = productDetailRepository.toggleFavorite()

  fun onAddToCartClick() = productDetailRepository.addToCart()
}
