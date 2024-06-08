package home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import domain.model.Item
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.atb
import priceninjakmp.composeapp.generated.resources.atb_bage
import priceninjakmp.composeapp.generated.resources.blyzenko_bage
import priceninjakmp.composeapp.generated.resources.card
import priceninjakmp.composeapp.generated.resources.scan
import priceninjakmp.composeapp.generated.resources.silpo
import priceninjakmp.composeapp.generated.resources.silpo_bage
import priceninjakmp.composeapp.generated.resources.trash
import pxToDp
import utils.Shop

@Composable
fun ItemsList(itemsList: List<Item>, onItemClick: (Item) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(itemsList.size) {
            Item(item = itemsList[it], onItemClick = onItemClick)
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

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Item(item: Item, onItemClick: (Item) -> Unit) {
    Box(
        modifier = Modifier.clickable(
            onClick = { onItemClick(item) },
            indication = null,
            interactionSource = remember { MutableInteractionSource() })
    ) {
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
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
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
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Medium
                ),
            )

        }
        Image(
            painter = when (item.shop) {
                Shop.SILPO.name -> painterResource(Res.drawable.silpo_bage)
                Shop.ATB.name -> painterResource(Res.drawable.atb_bage)
                Shop.BLYZENKO.name -> painterResource(Res.drawable.blyzenko_bage)
                else -> painterResource(Res.drawable.card)
            },
            contentDescription = "Item Bage",
            modifier = Modifier.size(56.dp).padding(top = 4.dp, start = 4.dp)
        )
    }

}