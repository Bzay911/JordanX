import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jordanx.availableCategories.AvailableCategories
import com.example.jordanx.product.ProductDataSource
import com.example.jordanx.product.ProductsSection
import com.example.jordanx.repo.cart.CartViewModel


@Composable
fun AdidasScreen(navController: NavController, cartViewModel: CartViewModel) {

    // to initialise the products from firebase on the beginning
    LaunchedEffect(Unit){
        cartViewModel.fetchCartProductsFromFirebase("adidas", "adidasShoes")
    }

    // After products are fetched it updates the state and we are access the updated data
    val items by cartViewModel.cartProducts.collectAsStateWithLifecycle()
    LazyColumn(modifier = Modifier
        .fillMaxSize())

    {
        item{
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically)
            {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back Button",
                    modifier = Modifier.size(32.dp)
                        .clickable { navController.popBackStack() })

                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "Search button",
                    modifier = Modifier.size(32.dp))
            }
        }

        item{
            Text(text = "Adidas",
                fontSize = 32.sp,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold)
        }

        item{
            AvailableCategories()
        }

        item {
            ProductsSection(products = items) { clickedProduct ->
                navController.navigate(
                    "productDetails/${Uri.encode(clickedProduct.productName)}/${Uri.encode(clickedProduct.productPrice.toString())}/${Uri.encode(clickedProduct.size)}/${Uri.encode(clickedProduct.color)}/${Uri.encode(clickedProduct.productImage)}"
                )
            }
        }
    }
}

