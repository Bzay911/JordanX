package com.example.jordanx.repo.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jordanx.product.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val favouritesDao: FavouritesDao
): ViewModel()
{
    private val _favouritesProducts = MutableStateFlow<List<Favourite>>(emptyList())
    val favouritesProducts: StateFlow<List<Favourite>> = _favouritesProducts

    init {
        fetchFavouritesProducts()
    }

    fun addToFavourites(favourite: Favourite){
        viewModelScope.launch {
            favouritesDao.addToFavourites(favourite)
            fetchFavouritesProducts()
        }
    }


    private fun fetchFavouritesProducts() {
        viewModelScope.launch {
            _favouritesProducts.value = favouritesDao.getAllFavourites()
        }
    }

    fun removeProduct(favourite: Favourite){
        viewModelScope.launch {
            favouritesDao.deleteFavourite(favourite.fId)
            fetchFavouritesProducts()
        }
    }

    suspend fun isProductInFavourites(fId: Int): Boolean {
        val favourite = favouritesDao.getFavouritesById(fId)
        return favourite != null
    }
}