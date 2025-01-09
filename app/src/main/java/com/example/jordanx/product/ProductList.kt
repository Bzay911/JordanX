package com.example.jordanx.product


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProductsSection(
    products: List<Product>,
    onProductClick: (Product) -> Unit)
{
    LazyColumn(modifier = Modifier.height(700.dp)) {
        items(products){product ->
            ProductItem(product, onProductClick)
        }
    }
}

@Composable
fun ProductItem(product: Product, onProductClick: (Product) -> Unit) {



    Box (
        modifier = Modifier
            .padding(8.dp)

            .clickable {
                onProductClick(product)
            }
    )
    {
        Column {
            Column(modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(Color.LightGray)
                .height(160.dp)
                .fillMaxWidth()
                .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween)
            {
                Row (modifier = Modifier.fillMaxWidth())
                {

                    AsyncImage(
                        model = product.productImage,
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(25.dp))
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column (modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center){

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = product.productName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp)


                        Row (modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(32.dp))
                        {

                            Text(text = " Price: ${product.productPrice}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)

                            Text(text = " Size: ${product.size}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)
                        }

                        Text(text = " Color: ${product.color}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
