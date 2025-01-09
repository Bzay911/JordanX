package com.example.jordanx.bottomNav

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

val items = listOf(
    BottomNavigationItems(
        title = "Home",
        icon = Icons.Rounded.Home,
        route = "home"
    ),

    BottomNavigationItems(
        title = "Cart",
        icon = Icons.Rounded.ShoppingCart,
        route = "cart"
    ),

    BottomNavigationItems(
        title = "Favourites",
        icon = Icons.Rounded.Favorite,
        route = "favourites"
    ),

    BottomNavigationItems(
        title = "Profile",
        icon = Icons.Rounded.AccountCircle,
        route = "profile"
    )
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        if(currentRoute != item.route){
                            navController.navigate(item.route){
                                popUpTo("home"){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = { Text(text = item.title) }
                )
            }

    }

}
