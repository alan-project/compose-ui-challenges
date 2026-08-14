package com.example.checkout.data.repository

import com.example.checkout.data.model.CheckoutContent
import com.example.checkout.data.model.CheckoutRequest
import com.example.checkout.data.model.OrderConfirmation

interface CheckoutRepository {
    fun getCheckoutContent(): CheckoutContent

    fun submitOrder(request: CheckoutRequest): OrderConfirmation
}
