package com.example.composemarket.ui.composemarket

import androidx.lifecycle.ViewModel
import com.example.composemarket.data.repository.ProductRepository

class ComposeMarketViewModel(
    private val productRepository: ProductRepository,
) : ViewModel()
