package com.example.jordanx.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jordanx.repo.cart.CartViewModel
import com.example.jordanx.repo.favourite.Favourite
import com.example.jordanx.repo.favourite.FavouritesViewModel
import com.example.jordanx.ui.theme.RatingStar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
//    fId: Int,
    productName: String,
    productPrice: Double,
    productImage: String,
    color: String,
    size:String,
    navController: NavController,
    cartViewModel: CartViewModel,
    favouritesViewModel: FavouritesViewModel,
) {

    var buttonState by remember { mutableStateOf(false) }

    // Checking if the product is already in wishlist
//    LaunchedEffect(fId) {
//        val isInWishList = favouritesViewModel.isProductInFavourites(fId)
//        buttonState = isInWishList
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(productName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.KeyboardArrowLeft,
                            "Back",
                            modifier = Modifier.size(32.dp))
                    }
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Default.Share,
                            "Share",
                            modifier = Modifier.size(32.dp))
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            // Product Image
            AsyncImage(
                model = productImage,
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
            )

            // Product Details
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                // Size and Color Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Size Dropdown
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { /* Handle size selection */ },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(size)
//                            Icon(Icons.Default.ArrowDropDown, "Select size") Commented for future use will be implemented in future
                        }
                    }

                    // Color Dropdown
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { /* Handle color selection */ },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(color)
//                            Icon(Icons.Default.ArrowDropDown, "Select color") Commented for future development
                        }
                    }

                    // Wishlist Icon
                    IconButton(onClick = {
                        val favourite = Favourite(
                            fProductName = productName,
                            fProductPrice = productPrice,
                            fColor = color,
                            fSize = size,
                            fProductImage = productImage)

                        if(buttonState){
                            favouritesViewModel.removeProduct(favourite)
                        }
                        else{
                            favouritesViewModel.addToFavourites(favourite)
                    }
                    }
                    ) {
                        Icon(
                        if (buttonState) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                        if (buttonState) "Remove from wishlist" else "Add to wishlist",
                        tint = if (buttonState) Color.Red else Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Brand and Price
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = productName,
                    )
                    Text(
                        text = "$ $productPrice",
                    )
                }

                // Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    repeat(5) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = RatingStar
                        )
                    }
                    Text(
                        text = "(10)",
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                // Product Description
                Text(
                    text = "Make it yours by today!!",
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Add to Cart Button
                Button(
                    onClick = {
                        val product = Product(
//                            productId = productId,
                            productName = productName,
                            productPrice = productPrice,
                            color = color,
                            size = size,
                            productImage = productImage
                        )
                        cartViewModel.addProducts(product)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                   colors = ButtonDefaults.buttonColors(
                       containerColor = Color.Red
                   )
                ) {
                    Text(
                        "ADD TO CART",
                        color = Color.White
                    )
                }
            }
        }
    }
}




