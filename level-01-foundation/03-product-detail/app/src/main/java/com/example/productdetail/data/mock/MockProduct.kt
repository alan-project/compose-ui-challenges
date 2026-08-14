package com.example.productdetail.data.mock

import com.example.productdetail.data.model.Product
import com.example.productdetail.data.model.ProductColor
import com.example.productdetail.data.model.ProductDetail

object MockProduct {
  val detail =
    ProductDetail(
      product =
        Product(
          id = 1,
          name = "AeroRun Cloud Sneakers",
          category = "Running Shoes",
          description =
            "Lightweight everyday runners with a breathable knit upper, responsive cushioning, and a durable rubber outsole built for city miles.",
          price = 139.99,
          rating = 4.8,
          reviewCount = 2_418,
          colors = ProductColor.entries,
          sizes = listOf(7, 8, 9, 10, 11, 12),
        ),
      selectedColor = ProductColor.MIDNIGHT,
      selectedSize = 9,
    )
}
