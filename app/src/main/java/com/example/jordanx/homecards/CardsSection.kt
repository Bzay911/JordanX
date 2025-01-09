package com.example.jordanx.homecards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val cards: List<Card> = listOf(
    Card(
        promoText = "Special offer",
        adsText = "50% discount on all Jordans",
    ),

    Card(
        promoText = "Heavy Discount",
        adsText = "50% discount on all Nikes",
    ),

    Card(
        promoText = "Special offer",
        adsText = "Half price on all Adidas",
    ),

    Card(
        promoText = "Low fee",
        adsText = "After pay on Lacoste",
    )
)

@Composable
fun CardsSection() {
    LazyRow {
        items(cards.size){ index ->
            CardItem(index)
        }
    }
}

@Composable
fun CardItem(
   index: Int,
){
    val card = cards[index]
    var lastItemPaddingEnd = 0.dp
    if(index == cards.size -1){
        lastItemPaddingEnd = 16.dp
    }

    Box(modifier = Modifier
        .padding(start = 8.dp,
            end = lastItemPaddingEnd))
    {
        val gradient = Brush.verticalGradient(
            colors = listOf(Color.Gray, Color.White)
        )

        Column(modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .background(gradient)
            .height(160.dp)
            .width(250.dp)
            .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween)
        {
            Text(
                text = card.promoText,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold)

            Text(
                text = card.adsText,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold)

            Button(
                modifier = Modifier,
                onClick = { })
            {
                Text(text = "Click me!")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardsSectionPreview() {
    CardsSection()
}