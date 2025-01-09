package com.example.jordanx.repo.favourite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites_table")
data class Favourite (
    @PrimaryKey(autoGenerate = true)
    val fId: Int = 0,
    val fProductName: String,
    val fProductImage: String,
    val fProductPrice: Double,
    val fColor: String,
    val fSize: String,
)