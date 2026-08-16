package com.example.productgrid.data.model

enum class ProductImage {
    HEADPHONES, KEYBOARD, SMARTWATCH, SPEAKER, CAMERA, MOUSE, E_READER, PROJECTOR, CHARGING_HUB, MICROPHONE, BACKPACK, DESK_LAMP, TRAVEL_BOTTLE, PHOTO_PRINTER, TABLET_STAND, AIR_PURIFIER, HANDHELD_CONSOLE, MINI_VACUUM, YOGA_MAT, COFFEE_GRINDER, CHARGING_PAD, KITCHEN_SCALE, ALARM_CLOCK, TRAVEL_UMBRELLA, BINOCULARS, COOKWARE, PLANT_POT, RUNNING_SHOES, SUNGLASSES, TRAVEL_CASE,
}

data class Product(
    val id: Long,
    val name: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val reviewCount: Int,
    val discountPercent: Int = 0,
    val isFavorite: Boolean = false,
    val image: ProductImage,
)
