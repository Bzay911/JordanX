package com.example.jordanx.repo.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jordanx.product.Product

@Dao
interface CartDao{

    // Inserting item in the cart
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: Product)

    // Getting all the items in the cart
    @Query("SELECT * FROM cart_products")
    suspend fun getCartItems(): List<Product>

    // Deleting an item from the cart by id
    @Query("DELETE FROM cart_products WHERE productId = :productId")
    suspend fun deleteProduct(productId: Int)

    // Deleting all items from the cart
    @Query("DELETE FROM cart_products")
    suspend fun clearCart()

}