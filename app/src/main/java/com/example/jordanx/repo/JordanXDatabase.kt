package com.example.jordanx.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jordanx.product.Product
import com.example.jordanx.repo.cart.CartDao
import com.example.jordanx.repo.favourite.Favourite
import com.example.jordanx.repo.favourite.FavouritesDao

@Database(entities = [Product::class, Favourite::class], version = 3, exportSchema = false)
abstract class JordanXDatabase: RoomDatabase(){
    abstract fun cartDao(): CartDao

    abstract fun favouritesDao(): FavouritesDao

    companion object{
        @Volatile
        private var INSTANCE: JordanXDatabase? = null

        fun getDatabase(context: Context): JordanXDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JordanXDatabase::class.java,
                    "cart_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE =instance
                instance
            }
        }
    }
}