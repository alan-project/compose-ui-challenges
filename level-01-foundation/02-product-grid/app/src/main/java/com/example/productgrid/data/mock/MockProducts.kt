package com.example.productgrid.data.mock

import com.example.productgrid.data.model.Product
import com.example.productgrid.data.model.ProductImage

object MockProducts {
  val items =
    listOf(
      Product(1, "Noise-Cancelling Headphones", "Audio", 249.99, 4.8, 1_842, 20, true, ProductImage.HEADPHONES),
      Product(2, "Mechanical Keyboard", "Computers", 129.99, 4.7, 924, 15, false, ProductImage.KEYBOARD),
      Product(3, "Smart Fitness Watch", "Wearables", 189.99, 4.6, 2_103, 10, false, ProductImage.SMARTWATCH),
      Product(4, "Portable Bluetooth Speaker", "Audio", 79.99, 4.5, 1_276, 0, true, ProductImage.SPEAKER),
      Product(5, "Compact Digital Camera", "Cameras", 599.99, 4.9, 638, 12, false, ProductImage.CAMERA),
      Product(6, "Wireless Gaming Mouse", "Computers", 89.99, 4.7, 1_445, 25, false, ProductImage.MOUSE),
      Product(7, "E-Reader Paper Display", "Electronics", 159.99, 4.8, 3_017, 0, true, ProductImage.E_READER),
      Product(8, "Mini Home Projector", "Electronics", 319.99, 4.4, 782, 18, false, ProductImage.PROJECTOR),
      Product(9, "USB-C Charging Hub", "Accessories", 54.99, 4.6, 2_489, 0, false, ProductImage.CHARGING_HUB),
      Product(10, "Studio Podcast Microphone", "Audio", 149.99, 4.7, 867, 20, true, ProductImage.MICROPHONE),
      Product(11, "Everyday Laptop Backpack", "Bags", 69.99, 4.5, 1_125, 10, false, ProductImage.BACKPACK),
      Product(12, "Smart LED Desk Lamp", "Home", 64.99, 4.4, 954, 0, false, ProductImage.DESK_LAMP),
      Product(13, "Insulated Travel Bottle", "Lifestyle", 34.99, 4.8, 4_210, 15, true, ProductImage.TRAVEL_BOTTLE),
      Product(14, "Instant Photo Printer", "Cameras", 119.99, 4.3, 731, 0, false, ProductImage.PHOTO_PRINTER),
      Product(15, "Adjustable Tablet Stand", "Accessories", 42.99, 4.6, 1_608, 20, false, ProductImage.TABLET_STAND),
      Product(16, "Compact Air Purifier", "Home", 139.99, 4.5, 1_018, 12, false, ProductImage.AIR_PURIFIER),
      Product(17, "Retro Handheld Console", "Gaming", 99.99, 4.7, 2_642, 0, true, ProductImage.HANDHELD_CONSOLE),
      Product(18, "Cordless Mini Vacuum", "Home", 84.99, 4.2, 689, 15, false, ProductImage.MINI_VACUUM),
      Product(19, "Premium Yoga Mat", "Fitness", 59.99, 4.8, 1_893, 0, false, ProductImage.YOGA_MAT),
      Product(20, "Electric Coffee Grinder", "Kitchen", 74.99, 4.6, 1_304, 20, true, ProductImage.COFFEE_GRINDER),
      Product(21, "Wireless Charging Pad", "Accessories", 39.99, 4.4, 2_276, 10, false, ProductImage.CHARGING_PAD),
      Product(22, "Digital Kitchen Scale", "Kitchen", 29.99, 4.7, 3_402, 0, false, ProductImage.KITCHEN_SCALE),
      Product(23, "Modern Alarm Clock", "Home", 44.99, 4.3, 817, 15, false, ProductImage.ALARM_CLOCK),
      Product(24, "Foldable Travel Umbrella", "Lifestyle", 27.99, 4.5, 1_989, 0, true, ProductImage.TRAVEL_UMBRELLA),
      Product(25, "Compact Binoculars", "Outdoors", 109.99, 4.6, 576, 18, false, ProductImage.BINOCULARS),
      Product(26, "Stainless Cookware Set", "Kitchen", 219.99, 4.8, 948, 20, false, ProductImage.COOKWARE),
      Product(27, "Desktop Plant Pot", "Home", 24.99, 4.7, 1_512, 0, false, ProductImage.PLANT_POT),
      Product(28, "Trail Running Shoes", "Fitness", 134.99, 4.6, 2_018, 15, true, ProductImage.RUNNING_SHOES),
      Product(29, "Classic Sunglasses", "Accessories", 89.99, 4.5, 1_047, 0, false, ProductImage.SUNGLASSES),
      Product(30, "Carry-On Travel Case", "Travel", 179.99, 4.7, 1_386, 10, false, ProductImage.TRAVEL_CASE),
    )
}
