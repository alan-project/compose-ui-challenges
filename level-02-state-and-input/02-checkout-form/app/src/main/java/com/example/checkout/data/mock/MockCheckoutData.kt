package com.example.checkout.data.mock

import com.example.checkout.data.model.CheckoutContent
import com.example.checkout.data.model.OrderItem
import com.example.checkout.data.model.PaymentMethod
import com.example.checkout.data.model.PaymentType
import com.example.checkout.data.model.ProductImage
import com.example.checkout.data.model.ShippingOption

object MockCheckoutData {
    const val STANDARD_SHIPPING_ID = "standard"
    const val EXPRESS_SHIPPING_ID = "express"
    const val CARD_PAYMENT_ID = "card-4242"
    const val WALLET_PAYMENT_ID = "wallet"

    val content =
        CheckoutContent(
            items =
                listOf(
                    OrderItem(
                        id = "auralis-studio",
                        name = "Auralis Studio",
                        variant = "Matte black · Wireless",
                        unitPriceCents = 18_900,
                        quantity = 1,
                        image = ProductImage.HEADPHONES,
                    ),
                ),
            shippingOptions =
                listOf(
                    ShippingOption(
                        id = STANDARD_SHIPPING_ID,
                        name = "Standard delivery",
                        description = "Carbon-neutral ground shipping",
                        arrivalEstimate = "Arrives Aug 18–20",
                        priceCents = 0,
                        badge = "Popular",
                    ),
                    ShippingOption(
                        id = EXPRESS_SHIPPING_ID,
                        name = "Express delivery",
                        description = "Priority tracked shipping",
                        arrivalEstimate = "Arrives tomorrow",
                        priceCents = 1_400,
                    ),
                ),
            paymentMethods =
                listOf(
                    PaymentMethod(
                        id = CARD_PAYMENT_ID,
                        type = PaymentType.CARD,
                        name = "Visa ending in 4242",
                        description = "Expires 08/29",
                    ),
                    PaymentMethod(
                        id = WALLET_PAYMENT_ID,
                        type = PaymentType.DIGITAL_WALLET,
                        name = "Digital wallet",
                        description = "Pay with your saved wallet",
                    ),
                ),
            taxRateBasisPoints = 1_300,
        )
}
