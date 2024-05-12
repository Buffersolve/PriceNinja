package home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.model.Item
import pxToDp

@Composable
fun ItemsList(itemsList: List<Item>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(itemsList.size) {
            Item(item = itemsList[it])
        }
        item {
            Spacer(
                modifier = Modifier
                    .padding(
                        bottom = pxToDp(
                            WindowInsets.navigationBars.getBottom(
                                LocalDensity.current
                            )
                        ).dp + 84.dp
                    )
            )
        }
    }

}

@Composable
fun Item(item: Item) {
    AsyncImage(
        model = item.image,
        contentDescription = "Item Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    )
}