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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jordanx.product.Product
import com.example.jordanx.repo.cart.CartViewModel
import com.example.jordanx.repo.favourite.Favourite
import com.example.jordanx.repo.favourite.FavouritesViewModel

@Composable
fun FavouritesScreen(favouritesViewModel: FavouritesViewModel) {

    val favouritesProducts by favouritesViewModel.favouritesProducts.collectAsState()

    LazyColumn (verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(8.dp)) {
        if (favouritesProducts.isEmpty()) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .fillParentMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Cart Icon",
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Your wishlist is empty")
                }
            }
        }

        else {
            items(favouritesProducts) { favourite ->
                FavouritesItem(favourite = favourite, favouritesViewModel = favouritesViewModel)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}



@Composable
fun FavouritesItem(favourite: Favourite,
             favouritesViewModel: FavouritesViewModel
) {

    Row (verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
            .padding(8.dp)
    ){
        AsyncImage(
            model = favourite.fProductImage,
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
            Text(text = favourite.fProductName)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "$ ${favourite.fProductPrice}")
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = favourite.fSize)
        }

        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Delete Icon",
            modifier = Modifier.size(28.dp)
                .clickable { favouritesViewModel.removeProduct(favourite)},
            tint = Color.Red)
    }


}
