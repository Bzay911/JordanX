package com.example.jordanx.screens

import android.Manifest
import android.app.Application
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.jordanx.product.Product
import com.example.jordanx.voiceparser.VoiceToTextParser


@Composable
fun ProfileScreen() {

    val context = LocalContext.current.applicationContext as Application
    val voiceToTextParser = remember { VoiceToTextParser(context) }

    var canRecord by remember { mutableStateOf(false) }
    var products by remember { mutableStateOf(emptyList<Product>()) }

    // Requesting audio record permission
    val recordAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted -> canRecord = isGranted }
    )

    LaunchedEffect(Unit) {
        recordAudioLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    val state by voiceToTextParser.state.collectAsState()

    // Use spokenText as searchQuery directly
    LaunchedEffect(state.spokenText) {
        Log.i("SpeechRecognition", "Spoken Text: ${state.spokenText}")
        if (state.spokenText.isNotEmpty()) {
            fetchProductsFromFirebase(state.spokenText) { fetchedProducts ->
                // Handle the fetched products, e.g., display them in the UI
                products = fetchedProducts
                fetchedProducts.forEach {
                    Log.i("Product", "Found: ${it.productName}")
                }
            }
        }
    }

        // Scaffold with floating action button
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    if (state.isSpeaking) {
                        voiceToTextParser.stopListening()
                    } else {
                        voiceToTextParser.startListening("en-US") // Use the correct language code
                    }
                }) {
                    AnimatedContent(targetState = state.isSpeaking) { isSpeaking ->
                        if (isSpeaking) {
                            Icon(imageVector = Icons.Default.Stop, contentDescription = null)
                        } else {
                            Icon(imageVector = Icons.Rounded.Mic, contentDescription = null)
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedContent(targetState = state.isSpeaking) { isSpeaking ->
                    if (isSpeaking) {
                        Text(text = "Listening...")
                    } else {
                        // Display the spoken text or a placeholder
                        Text(
                            text = state.spokenText.ifEmpty { "Click to search" },
                            fontSize = 24.sp
                        )
                    }
                }
                if (products.isNotEmpty()) {
                ReturnedProduct(products)
                }

            }
        }
    }


@Composable
fun ReturnedProduct(products: List<Product>) {
    if (products.isNotEmpty()) {
        LazyColumn {
            items(products) { product ->
                FirebaseProduct(product)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    } else {
        // If no products are found, display a message
        Text(color = Color.LightGray,
            fontSize = 12.sp,
            text = "No products found",
        )
    }
}

@Composable
fun FirebaseProduct(product: Product) {

    Row (verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
            .padding(8.dp)
    ){
        AsyncImage(
            model = product.productImage,
            contentDescription = "Product Image",
            modifier = Modifier
                .size(100.dp)
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)
        ) {
            Text(text = product.productName)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "$ ${product.productPrice}")
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = product.size)
        }
    }


}