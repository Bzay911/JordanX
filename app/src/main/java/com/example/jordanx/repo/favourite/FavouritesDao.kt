package com.example.jordanx.repo.favourite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jordanx.product.Product

@Dao
interface FavouritesDao {

    // Inserting item in the cart
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourites(product: Favourite)

    // Getting all the items in the cart
    @Query("SELECT * FROM favourites_table")
    suspend fun getAllFavourites(): List<Favourite>

    // Deleting an item from the cart by id
    @Query("DELETE FROM favourites_table WHERE fId = :fId")
    suspend fun deleteFavourite(fId: Int)

    // To check weather it is in the wishlist or not
    @Query("SELECT * FROM favourites_table WHERE fId = :fId")
    suspend fun getFavouritesById(fId: Int): Favourite?
}