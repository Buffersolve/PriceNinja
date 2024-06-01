package home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import domain.model.Item
import pxToDp

@Composable
fun ItemsList(itemsList: List<Item>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(itemsList.size) {
            Item(item = itemsList[it])
        }
        items(3) {
            Spacer(
                modifier = Modifier
                    .padding(
                        bottom = pxToDp(
                            WindowInsets.navigationBars.getBottom(
                                LocalDensity.current
                            )
                        ).dp + 110.dp
                    )
            )
        }
    }

}

@Composable
fun Item(item: Item) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            model = item.image,
            contentDescription = "Item Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            text = item.name,
            modifier = Modifier,
            style = TextStyle(fontSize = 15.sp, color = Color.Black, fontWeight = FontWeight.Medium)
        )
        if (item.oldPrice != null) {
            Text(
                text = item.oldPrice?.toString() + " грн",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough,
                )
            )
        }
        Text(
            text = item.discountPrice.toString() + " грн",
            style = TextStyle(fontSize = 15.sp, color = Color.Red, fontWeight = FontWeight.Medium),
        )

    }
}