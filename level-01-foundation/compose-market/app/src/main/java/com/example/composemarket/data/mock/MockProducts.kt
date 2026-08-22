package com.example.composemarket.data.mock

import com.example.composemarket.data.model.Product

object MockProducts {
    // Curated Unsplash CDN images keep each mock product deterministic and relevant.
    private val ProductImageIds = mapOf(
        "headphones" to "https://images.unsplash.com/photo-1505740420928-5e560c06d30e",
        "mechanical-keyboard" to "https://images.unsplash.com/photo-1587829741301-dc798b83add3",
        "smartwatch" to "https://images.unsplash.com/photo-1523275335684-37898b6baf30",
        "bluetooth-speaker" to "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1",
        "digital-camera" to "https://images.unsplash.com/photo-1516035069371-29a1b244cc32",
        "computer-mouse" to "https://images.unsplash.com/photo-1527814050087-3793815479db",
        "e-reader" to "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0",
        "projector" to "https://images.unsplash.com/photo-1535016120720-40c646be5580",
        "usb-charging-hub" to "https://images.unsplash.com/photo-1625842268584-8f3296236761",
        "podcast-microphone" to "https://images.unsplash.com/photo-1590602847861-f357a9332bbc",
        "laptop-backpack" to "https://images.unsplash.com/photo-1553062407-98eeb64c6a62",
        "desk-lamp" to "https://images.unsplash.com/photo-1507473885765-e6ed057f782c",
        "travel-bottle" to "https://images.unsplash.com/photo-1602143407151-7111542de6e8",
        "photo-printer" to "https://images.unsplash.com/photo-1606983340126-99ab4feaa64a",
        "tablet-stand" to "https://images.unsplash.com/photo-1561154464-82e9adf32764",
        "air-purifier" to "https://images.unsplash.com/photo-1581578731548-c64695cc6952",
        "handheld-game-console" to "https://images.unsplash.com/photo-1592840496694-26d035b52b48",
        "mini-vacuum" to "https://images.unsplash.com/photo-1558317374-067fb5f30001",
        "yoga-mat" to "https://images.unsplash.com/photo-1599447421416-3414500d18a5",
        "coffee-grinder" to "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085",
        "wireless-charging-pad" to "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9",
        "kitchen-scale" to "https://images.unsplash.com/photo-1556911220-bff31c812dba",
        "alarm-clock" to "https://images.unsplash.com/photo-1508057198894-247b23fe5ade",
        "travel-umbrella" to "https://images.unsplash.com/photo-1512428559087-560fa5ceab42",
        "binoculars" to "https://images.unsplash.com/photo-1533130061792-64b345e4a833",
        "cookware" to "https://images.unsplash.com/photo-1556910103-1c02745aae4d",
        "plant-pot" to "https://images.unsplash.com/photo-1485955900006-10f4d324d411",
        "running-shoes" to "https://images.unsplash.com/photo-1542291026-7eec264c27ff",
        "sunglasses" to "https://images.unsplash.com/photo-1572635196237-14b3f281503f",
        "travel-suitcase" to "https://images.unsplash.com/photo-1553531384-cc64ac80f931",
    )

    val items = listOf(
        Product(
            1,
            "Noise-Cancelling Headphones",
            "Audio",
            249.99,
            4.8,
            1_842,
            20,
            true,
            productImageUrl("headphones", 1)
        ),
        Product(
            2,
            "Mechanical Keyboard",
            "Computers",
            129.99,
            4.7,
            924,
            15,
            false,
            productImageUrl("mechanical-keyboard", 2)
        ),
        Product(
            3,
            "Smart Fitness Watch",
            "Wearables",
            189.99,
            4.6,
            2_103,
            10,
            false,
            productImageUrl("smartwatch", 3)
        ),
        Product(
            4,
            "Portable Bluetooth Speaker",
            "Audio",
            79.99,
            4.5,
            1_276,
            0,
            true,
            productImageUrl("bluetooth-speaker", 4)
        ),
        Product(
            5, "Compact Digital Camera", "Cameras", 599.99, 4.9, 638, 12, false, productImageUrl("digital-camera", 5)
        ),
        Product(
            6,
            "Wireless Gaming Mouse",
            "Computers",
            89.99,
            4.7,
            1_445,
            25,
            false,
            productImageUrl("computer-mouse", 6)
        ),
        Product(
            7,
            "E-Reader Paper Display",
            "Electronics",
            159.99,
            4.8,
            3_017,
            0,
            true,
            productImageUrl("e-reader", 7)
        ),
        Product(
            8,
            "Mini Home Projector",
            "Electronics",
            319.99,
            4.4,
            782,
            18,
            false,
            productImageUrl("projector", 8)
        ),
        Product(
            9,
            "USB-C Charging Hub",
            "Accessories",
            54.99,
            4.6,
            2_489,
            0,
            false,
            productImageUrl("usb-charging-hub", 9)
        ),
        Product(
            10,
            "Studio Podcast Microphone",
            "Audio",
            149.99,
            4.7,
            867,
            20,
            true,
            productImageUrl("podcast-microphone", 10)
        ),
        Product(
            11,
            "Everyday Laptop Backpack",
            "Bags",
            69.99,
            4.5,
            1_125,
            10,
            false,
            productImageUrl("laptop-backpack", 11)
        ),
        Product(
            12, "Smart LED Desk Lamp", "Home", 64.99, 4.4, 954, 0, false, productImageUrl("desk-lamp", 12)
        ),
        Product(
            13,
            "Insulated Travel Bottle",
            "Lifestyle",
            34.99,
            4.8,
            4_210,
            15,
            true,
            productImageUrl("travel-bottle", 13)
        ),
        Product(
            14,
            "Instant Photo Printer",
            "Cameras",
            119.99,
            4.3,
            731,
            0,
            false,
            productImageUrl("photo-printer", 14)
        ),
        Product(
            15,
            "Adjustable Tablet Stand",
            "Accessories",
            42.99,
            4.6,
            1_608,
            20,
            false,
            productImageUrl("tablet-stand", 15)
        ),
        Product(
            16,
            "Compact Air Purifier",
            "Home",
            139.99,
            4.5,
            1_018,
            12,
            false,
            productImageUrl("air-purifier", 16)
        ),
        Product(
            17,
            "Retro Handheld Console",
            "Gaming",
            99.99,
            4.7,
            2_642,
            0,
            true,
            productImageUrl("handheld-game-console", 17)
        ),
        Product(
            18, "Cordless Mini Vacuum", "Home", 84.99, 4.2, 689, 15, false, productImageUrl("mini-vacuum", 18)
        ),
        Product(
            19, "Premium Yoga Mat", "Fitness", 59.99, 4.8, 1_893, 0, false, productImageUrl("yoga-mat", 19)
        ),
        Product(
            20,
            "Electric Coffee Grinder",
            "Kitchen",
            74.99,
            4.6,
            1_304,
            20,
            true,
            productImageUrl("coffee-grinder", 20)
        ),
        Product(
            21,
            "Wireless Charging Pad",
            "Accessories",
            39.99,
            4.4,
            2_276,
            10,
            false,
            productImageUrl("wireless-charging-pad", 21)
        ),
        Product(
            22,
            "Digital Kitchen Scale",
            "Kitchen",
            29.99,
            4.7,
            3_402,
            0,
            false,
            productImageUrl("kitchen-scale", 22)
        ),
        Product(
            23, "Modern Alarm Clock", "Home", 44.99, 4.3, 817, 15, false, productImageUrl("alarm-clock", 23)
        ),
        Product(
            24,
            "Foldable Travel Umbrella",
            "Lifestyle",
            27.99,
            4.5,
            1_989,
            0,
            true,
            productImageUrl("travel-umbrella", 24)
        ),
        Product(
            25,
            "Compact Binoculars",
            "Outdoors",
            109.99,
            4.6,
            576,
            18,
            false,
            productImageUrl("binoculars", 25)
        ),
        Product(
            26,
            "Stainless Cookware Set",
            "Kitchen",
            219.99,
            4.8,
            948,
            20,
            false,
            productImageUrl("cookware", 26)
        ),
        Product(
            27, "Desktop Plant Pot", "Home", 24.99, 4.7, 1_512, 0, false, productImageUrl("plant-pot", 27)
        ),
        Product(
            28,
            "Trail Running Shoes",
            "Fitness",
            134.99,
            4.6,
            2_018,
            15,
            true,
            productImageUrl("running-shoes", 28)
        ),
        Product(
            29,
            "Classic Sunglasses",
            "Accessories",
            89.99,
            4.5,
            1_047,
            0,
            false,
            productImageUrl("sunglasses", 29)
        ),
        Product(
            30,
            "Carry-On Travel Case",
            "Travel",
            179.99,
            4.7,
            1_386,
            10,
            false,
            productImageUrl("travel-suitcase", 30)
        ),
    )

    private fun productImageUrl(keyword: String, lock: Int): String =
        "${ProductImageIds.getValue(keyword)}?auto=format&fit=crop&w=640&q=80&sig=$lock"
}
