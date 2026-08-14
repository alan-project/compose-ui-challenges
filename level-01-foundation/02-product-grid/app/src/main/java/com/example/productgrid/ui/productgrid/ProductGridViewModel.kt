package com.example.productgrid.ui.productgrid

import androidx.lifecycle.ViewModel
import com.example.productgrid.data.repository.ProductRepository

class ProductGridViewModel(
  private val productRepository: ProductRepository,
) : ViewModel() {
}
