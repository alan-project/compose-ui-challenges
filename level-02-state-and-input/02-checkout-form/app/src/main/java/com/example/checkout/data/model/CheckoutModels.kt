package com.example.checkout.data.model

enum class ProductImage {
    HEADPHONES,
}

data class OrderItem(
    val id: String,
    val name: String,
    val variant: String,
    val unitPriceCents: Int,
    val quantity: Int,
    val image: ProductImage,
) {
    val lineTotalCents: Int
        get() = unitPriceCents * quantity
}

data class ShippingOption(
    val id: String,
    val name: String,
    val description: String,
    val arrivalEstimate: String,
    val priceCents: Int,
    val badge: String? = null,
)

enum class PaymentType {
    CARD,
    DIGITAL_WALLET,
}

data class PaymentMethod(
    val id: String,
    val type: PaymentType,
    val name: String,
    val description: String,
)

data class CheckoutContent(
    val items: List<OrderItem>,
    val shippingOptions: List<ShippingOption>,
    val paymentMethods: List<PaymentMethod>,
    val taxRateBasisPoints: Int,
) {
    init {
        require(shippingOptions.isNotEmpty()) { "Checkout requires at least one shipping option." }
        require(paymentMethods.isNotEmpty()) { "Checkout requires at least one payment method." }
    }

    val subtotalCents: Int
        get() = items.sumOf(OrderItem::lineTotalCents)
}

data class CheckoutRequest(
    val fullName: String,
    val email: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val shippingOptionId: String,
    val paymentMethodId: String,
    val totalCents: Int,
)

data class OrderConfirmation(
    val orderNumber: String,
    val email: String,
    val arrivalEstimate: String,
    val totalCents: Int,
)
