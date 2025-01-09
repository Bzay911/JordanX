package com.example.jordanx.screens

import android.Manifest
import android.app.Application
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jordanx.GridView
import com.example.jordanx.homecards.CardsSection
import com.example.jordanx.product.Product
import com.example.jordanx.voiceparser.VoiceToTextParser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onBrandClick: (String) -> Unit = {},
    navController: NavController
) {
    var searchResults by remember { mutableStateOf<List<Product>>(emptyList()) }
    var selectedBrand by remember { mutableStateOf("All") }


    LazyColumn(modifier = modifier
        .fillMaxSize()
        .padding(8.dp)
    )
    {
        // Top bar
        item{
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "JordanX",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)

                Icon(imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Profile Icon",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { })
            }
        }

//         Search bar
        item{
            SearchBarWithActions(
                onSearchClick = {query ->
                    fetchProductsFromFirebase(query) { products ->
                        searchResults = products
                    }

                },
                onMicClick = {
                    navController.navigate("profile")

                }
            )
        }
        item{
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
        }}

        if (searchResults.isNotEmpty()){
            items(searchResults){product ->
                Text(text = "${product.productName}, ${product.size}")
            }
        }
        else{
           item { Text(text = "Search Empty") }
        }
//         Brand categories
        item{
            BrandCategories(
                selectedBrand = selectedBrand,
                onBrandSelected = {brand ->
                    selectedBrand = brand
                    onBrandClick(brand)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            // Card section
            CardsSection()
        }

        item {
            // Popular
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Popular",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = "view all",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        item{
            GridView()
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "More from our sellers",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            CardsSection()
        }
    }
}

@Composable
fun BrandCategories(
    selectedBrand: String,
    onBrandSelected: (String) -> Unit
)
{
  val brandList = listOf(
      "All",
      "Nike",
      "Adidas",
      "Puma",
      "Sketchers",
      "Lacoste"
  )

    Row (modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically)
    {
        brandList.forEach{brand ->
            Button(onClick = {onBrandSelected(brand)},
                modifier = Modifier
                    .padding(8.dp)
            )
            {
                Text(text = brand)
            }
        }

    }
}

fun fetchProductsFromFirebase(searchQuery: String, onProductsFetched: (List<Product>) -> Unit) {
    val db = Firebase.firestore

    db.collection("Shoes").document("nike").collection("nikeShoes")
        .whereGreaterThanOrEqualTo("productName", searchQuery)
        .whereLessThanOrEqualTo("productName", searchQuery + "\uf8ff")  // End the search query with a high character to include all matches
        .addSnapshotListener { value, error ->
            if (error != null) {
                // Handle the error
                Log.e("Firebase", "Error fetching products", error)
                return@addSnapshotListener
            }

            if (value != null) {
                val products = value.toObjects(Product::class.java)
                onProductsFetched(products)
            }
        }
}

