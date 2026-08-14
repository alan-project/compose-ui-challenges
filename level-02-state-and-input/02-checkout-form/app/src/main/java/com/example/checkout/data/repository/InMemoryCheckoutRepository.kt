package com.example.checkout.data.repository

import com.example.checkout.data.mock.MockCheckoutData
import com.example.checkout.data.model.CheckoutContent
import com.example.checkout.data.model.CheckoutRequest
import com.example.checkout.data.model.OrderConfirmation

class InMemoryCheckoutRepository(
    private val content: CheckoutContent = MockCheckoutData.content,
) : CheckoutRepository {
    private val mutableSubmittedOrders = mutableListOf<CheckoutRequest>()

    val submittedOrders: List<CheckoutRequest>
        get() = mutableSubmittedOrders.toList()

    override fun getCheckoutContent(): CheckoutContent = content

    override fun submitOrder(request: CheckoutRequest): OrderConfirmation {
        mutableSubmittedOrders += request
        val shipping = content.shippingOptions.first { it.id == request.shippingOptionId }
        return OrderConfirmation(
            orderNumber = "LU-${1041 + mutableSubmittedOrders.size}",
            email = request.email,
            arrivalEstimate = shipping.arrivalEstimate,
            totalCents = request.totalCents,
        )
    }
}
