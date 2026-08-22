package com.example.composemarket.ui.composemarket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemarket.data.model.Product
import com.example.composemarket.data.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ComposeMarketUiState(
    val products: List<Product> = emptyList(),
)

class ComposeMarketViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    val uiState: StateFlow<ComposeMarketUiState> =
        productRepository.products.map(::ComposeMarketUiState).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ComposeMarketUiState(),
        )

    fun onFavoriteClick(productId: Long) {
        productRepository.toggleFavorite(productId)
    }
}
