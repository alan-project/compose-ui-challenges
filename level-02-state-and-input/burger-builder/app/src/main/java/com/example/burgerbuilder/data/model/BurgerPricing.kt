package com.example.burgerbuilder.data.model

fun calculateBurgerTotal(
    burger: Burger,
    isDoublePatty: Boolean,
    selectedExtraIds: Collection<String>,
    quantity: Int,
): Double {
    val pattyPrice = if (isDoublePatty) burger.doublePattyPrice else 0.0
    val extrasPrice =
        burger.extras
            .filter { option -> option.id in selectedExtraIds }
            .sumOf(BurgerOption::additionalPrice)

    return (burger.basePrice + pattyPrice + extrasPrice) * quantity.coerceAtLeast(1)
}
