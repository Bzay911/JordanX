import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jordanx.product.Product
import com.example.jordanx.repo.cart.CartViewModel

@Composable
fun CartScreen(cartViewModel: CartViewModel) {

    val products by cartViewModel.cartProducts.collectAsState()

    var totalPrice  = cartViewModel.calculateTotal(products)

    LazyColumn (verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(8.dp)) {
        if (products.isEmpty()) {
            item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillParentMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart Icon",
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Your cart is empty")
                    }
                }
        }

        else {
            items(products) { product ->
                CartItem(product = product, cartViewModel = cartViewModel)
                Spacer(modifier = Modifier.height(16.dp))
            }

            item{
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PriceLine(text = "Sub-total", price = totalPrice)
                PriceLine(text = "Delivery Fee", price = 0.00)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                PriceLine(text = "Total Cost", price = totalPrice, isTotal = true)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Checkout Button
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCB45))
            ) {
                Text("Checkout", color = Color.Black)
            }}


        }
    }

}



@Composable
fun CartItem(product: Product,
             cartViewModel: CartViewModel
) {

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

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Icon",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { cartViewModel.removeProduct(product) },
                tint = Color.Red)
        }


}

@Composable
fun PriceLine(
    text: String,
    price: Double,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = "$${String.format("%.2f", price)}",
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
    }
}