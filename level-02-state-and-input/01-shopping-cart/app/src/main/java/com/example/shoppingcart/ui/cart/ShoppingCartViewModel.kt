package com.example.shoppingcart.ui.cart

import androidx.lifecycle.ViewModel
import com.example.shoppingcart.data.repository.ShoppingCartRepository

class ShoppingCartViewModel(
  private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
}
