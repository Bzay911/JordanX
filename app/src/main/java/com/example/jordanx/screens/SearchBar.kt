package com.example.jordanx.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarWithActions(
    modifier: Modifier = Modifier,
    placeholderText: String = "Search...",
    onSearchClick: (String) -> Unit,
    onMicClick: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text(placeholderText, color = Color.Gray) },
        singleLine = true,
        trailingIcon = {
            Row {
                IconButton(onClick = { onSearchClick(searchText) }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Gray)
                }
                IconButton(onClick = { onMicClick() }) {
                    Icon(imageVector = Icons.Default.Mic, contentDescription = "Mic Icon", tint = Color.Gray)
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFF1F1F1),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
