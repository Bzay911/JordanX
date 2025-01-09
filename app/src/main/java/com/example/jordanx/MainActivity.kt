package com.example.jordanx

import AdidasScreen
import CartScreen
import FavouritesScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jordanx.bottomNav.BottomNavigationBar
import com.example.jordanx.repo.JordanXDatabase
import com.example.jordanx.repo.cart.CartViewModel
import com.example.jordanx.product.ProductDetailsScreen
import com.example.jordanx.repo.favourite.FavouritesViewModel
import com.example.jordanx.screens.HomeScreen
import com.example.jordanx.screens.LacosteScreen
import com.example.jordanx.screens.NikeScreen
import com.example.jordanx.screens.ProfileScreen
import com.example.jordanx.screens.PumaScreen
import com.example.jordanx.screens.SketchersScreen
import com.example.jordanx.ui.theme.JordanXTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JordanXTheme {
             MainNavigation()
            }
        }
    }
}

@Composable
fun MainNavigation(){
    val navController = rememberNavController()

    val context = LocalContext.current

    val cartDao = JordanXDatabase.getDatabase(context).cartDao()
    val cartViewModel = CartViewModel(cartDao)

    val favouritesDao = JordanXDatabase.getDatabase(context).favouritesDao()
    val favouritesViewModel = FavouritesViewModel(favouritesDao)


    Scaffold (
        bottomBar = { BottomNavigationBar(navController = navController)}
    ){ paddingValues ->

    NavHost(navController = navController, startDestination = "home", modifier = Modifier.padding(paddingValues)){
        composable("home"){
            HomeScreen(
                navController = navController,
                onBrandClick = {brand ->
                    navController.navigate("brand/$brand")
                },

            )
        }

        composable("cart"){
            CartScreen(cartViewModel)
        }

        composable("favourites"){
            FavouritesScreen(favouritesViewModel)
        }

        composable("profile"){
            ProfileScreen()
        }

        composable(
            "brand/{brandName}",
            arguments = listOf(navArgument("brandName"){type = NavType.StringType})
        ) { backStackEntry ->
            val brandName = backStackEntry.arguments?.getString("brandName") ?: "All"
            when(brandName){
                "Nike" -> NikeScreen(navController = navController, cartViewModel)
                "Adidas" -> AdidasScreen(navController = navController, cartViewModel)
                "Puma" -> PumaScreen(navController = navController, cartViewModel)
                "Sketchers" -> SketchersScreen(navController = navController, cartViewModel)
                "Lacoste" -> LacosteScreen(navController = navController, cartViewModel)
                else -> HomeScreen(navController = navController)
            }
        }

        composable("productDetails/{productName}/{productPrice}/{size}/{color}/{productImage}") { backStackEntry ->
            val productName = backStackEntry.arguments?.getString("productName")
            val productPrice = backStackEntry.arguments?.getString("productPrice")?.toDoubleOrNull() ?: 0.00
            val size = backStackEntry.arguments?.getString("size")
            val color = backStackEntry.arguments?.getString("color")
            val productImage = backStackEntry.arguments?.getString("productImage")
//            val fId = backStackEntry.arguments?.getString("fId")?.toInt()?:0


            ProductDetailsScreen(
                productName = productName ?: "",
                productPrice = productPrice,
                size = size ?: "",
                productImage = productImage?:"",
                color = color ?: "",
                navController = navController,
                cartViewModel = cartViewModel,
                favouritesViewModel = favouritesViewModel,
//                fId = fId
            )
        }

    }
    }
}