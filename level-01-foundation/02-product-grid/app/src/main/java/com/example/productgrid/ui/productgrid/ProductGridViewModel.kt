package com.example.productgrid.ui.productgrid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productgrid.data.model.Product
import com.example.productgrid.data.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ProductGridUiState(
    val products: List<Product> = emptyList(),
)

class ProductGridViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    val uiState: StateFlow<ProductGridUiState> =
        productRepository.products.map(::ProductGridUiState).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProductGridUiState(),
        )

    fun onFavoriteClick(productId: Long) {
        productRepository.toggleFavorite(productId)
    }
}
