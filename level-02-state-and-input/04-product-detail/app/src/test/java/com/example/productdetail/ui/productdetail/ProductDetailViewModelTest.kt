package com.example.productdetail.ui.productdetail

import com.example.productdetail.data.repository.InMemoryProductDetailRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductDetailViewModelTest {
  @Test
  fun actions_updateRepositoryState() {
    val repository = InMemoryProductDetailRepository()
    val viewModel = ProductDetailViewModel(repository)

    viewModel.onIncreaseQuantity()
    viewModel.onFavoriteClick()
    viewModel.onAddToCartClick()

    assertEquals(2, repository.detail.value.quantity)
    assertEquals(2, repository.detail.value.cartItemCount)
    assertTrue(repository.detail.value.isFavorite)
  }
}
