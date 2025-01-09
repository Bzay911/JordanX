package com.example.jordanx

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GridView() {
  LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      modifier = Modifier.heightIn(max = 500.dp),
      content = {
          items(8){ i ->
              Box(modifier = Modifier
                  .padding(8.dp)
                  .aspectRatio(1f)
                  .clip(RoundedCornerShape(5.dp))
                  .background(Color.Green),
                  contentAlignment = Alignment.Center)
              {
                  Text(
                      text = "Item $i",
                      fontSize = 16.sp,
                      color = Color.White
                  )
              }
          }
      }
  )
}