package com.example.composemarket.ui.composemarket

import com.example.composemarket.data.repository.ProductRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class ComposeMarketViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Test
  fun uiState_containsRepositoryProducts() = runTest {
    val repository = ProductRepositoryImpl()
    val viewModel = ComposeMarketViewModel(repository)
    val collector = backgroundScope.launch { viewModel.uiState.collect() }
    try {
      runCurrent()

      assertEquals(repository.products.first(), viewModel.uiState.value.products)
    } finally {
      collector.cancelAndJoin()
    }
  }

  @Test
  fun onFavoriteClick_togglesSelectedProduct() = runTest {
    val repository = ProductRepositoryImpl()
    val viewModel = ComposeMarketViewModel(repository)
    val collector = backgroundScope.launch { viewModel.uiState.collect() }
    try {
      runCurrent()
      val product = viewModel.uiState.value.products.first { !it.isFavorite }

      assertFalse(product.isFavorite)

      viewModel.onFavoriteClick(product.id)

      assertTrue(repository.products.first().first { it.id == product.id }.isFavorite)
    } finally {
      collector.cancelAndJoin()
    }
  }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
  private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
  override fun starting(description: Description) {
    Dispatchers.setMain(testDispatcher)
  }

  override fun finished(description: Description) {
    Dispatchers.resetMain()
  }
}
