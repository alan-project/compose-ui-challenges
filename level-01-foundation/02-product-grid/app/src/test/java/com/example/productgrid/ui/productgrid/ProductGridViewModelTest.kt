package com.example.productgrid.ui.productgrid

import com.example.productgrid.data.repository.InMemoryProductRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ProductGridViewModelTest {
  @Test
  fun uiState_containsRepositoryProducts() = runTest {
    val repository = InMemoryProductRepository()
    val viewModel = ProductGridViewModel(repository)

    assertEquals(repository.products.value, viewModel.uiState.value.products)
  }

  @Test
  fun onFavoriteClick_togglesSelectedProduct() = runTest {
    val repository = InMemoryProductRepository()
    val viewModel = ProductGridViewModel(repository)
    val product = viewModel.uiState.value.products.first { !it.isFavorite }

    assertFalse(product.isFavorite)

    viewModel.onFavoriteClick(product.id)

    assertTrue(repository.products.value.first { it.id == product.id }.isFavorite)
  }
}
