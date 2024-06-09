package product

import ShopMapper
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import maps.MapScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.location
import priceninjakmp.composeapp.generated.resources.search_near_map
import priceninjakmp.composeapp.generated.resources.silpo
import pxToDp
import utils.GrayNavNar

class ProductScreen(
    private val imageUrl: String,
    private val title: String,
    private val oldPrice: Double?,
    private val discountPrice: Double,
    private val shop: String
) : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxSize().padding(
                top = pxToDp(
                    WindowInsets.statusBars.getTop(
                        LocalDensity.current
                    )
                ).dp
            ).padding(
                horizontal = 16.dp
            )
        ) {

            // Back Button
            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp).fillMaxWidth().clip(shape = RoundedCornerShape(50))
                        .clickable {
                            navigator.pop()
                        }
                )
            }

            // Product image
            AsyncImage(
                model = imageUrl,
                contentDescription = "Item Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp).padding(top = 8.dp)
            )


            Text(
                text = title,
                modifier = Modifier.padding(top = 12.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (oldPrice != null) {
                Text(
                    text = "$oldPrice грн",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough,
                    )
                )
            }
            Text(
                text = "$discountPrice грн",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Medium
                ),
            )

            val shopRes = ShopMapper(shop)
            MapButton {
                navigator.push(MapScreen(shopRes))
            }

        }

    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun MapButton(
        onClick: () -> Unit
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(74.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .padding(top = 24.dp)
                .background(color = GrayNavNar, shape = RoundedCornerShape(8.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
        ) {
            Row(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
                Icon(
                    painter = painterResource(Res.drawable.location),
                    contentDescription = "Map",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    tint = Color.Black
                )
                Text(
                    stringResource(Res.string.search_near_map),
                    modifier = Modifier.align(Alignment.CenterVertically).padding(start = 8.dp),
                    color = Color.Black
                )
            }


        }
    }


}