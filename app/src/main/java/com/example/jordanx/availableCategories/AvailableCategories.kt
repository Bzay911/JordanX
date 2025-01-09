package com.example.jordanx.availableCategories

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AvailableCategories()
{
    val categories = listOf(
        "All",
        "Mens",
        "Womens",
        "Kids",
        "Unisex",
    )

    Row (modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically)
    {
        categories.forEach{category ->
            Button(onClick = {},
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            )
            {
                Text(text = category)
            }
        }

    }

}