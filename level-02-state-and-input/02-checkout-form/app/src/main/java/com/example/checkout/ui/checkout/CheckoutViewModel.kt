package com.example.checkout.ui.checkout

import androidx.lifecycle.ViewModel
import com.example.checkout.data.repository.CheckoutRepository

class CheckoutViewModel(
  private val checkoutRepository: CheckoutRepository,
) : ViewModel() {
}
