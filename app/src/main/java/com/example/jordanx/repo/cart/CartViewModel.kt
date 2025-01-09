package com.example.jordanx.repo.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jordanx.product.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class CartViewModel(
    private val cartDao: CartDao
): ViewModel() {

    private val _cartProducts = MutableStateFlow<List<Product>>(emptyList())
    val cartProducts: StateFlow<List<Product>> = _cartProducts

    fun fetchCartProductsFromFirebase(brand: String, subCollection: String){
        var db = Firebase.firestore

        db.collection("Shoes").document(brand).collection(subCollection)
            .addSnapshotListener{value, error ->
                if(error != null){
                    return@addSnapshotListener
                }

                if(value != null){
                    _cartProducts.value = value.toObjects()
                }
            }
    }

    fun addProducts(product: Product){
        viewModelScope.launch {
            cartDao.addProduct(product)
            Log.i("Cart", "Added product: ${product.productName}, Price: ${product.productPrice}")
            fetchCartProducts()
        }
    }

    fun calculateTotal(products: List<Product>): Double {
        return products.sumOf { it.productPrice }
    }

    private fun fetchCartProducts() {
        viewModelScope.launch {
            _cartProducts.value = cartDao.getCartItems()
        }
    }

    fun removeProduct(product: Product){
        viewModelScope.launch {
            cartDao.deleteProduct(product.productId)
            fetchCartProducts()
        }
    }

    fun clearCart(){
        viewModelScope.launch {
            cartDao.clearCart()
            fetchCartProducts()
        }

    }
}