package com.example.jordanx.product

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Int = 0,

    val productName: String = "",
    val productImage: String = "",
    val productPrice: Double = 0.0,
    val color: String = "",
    val size: String = ""
)
