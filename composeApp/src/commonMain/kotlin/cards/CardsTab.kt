package cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.atb
import priceninjakmp.composeapp.generated.resources.card
import priceninjakmp.composeapp.generated.resources.empty_card
import priceninjakmp.composeapp.generated.resources.plus
import priceninjakmp.composeapp.generated.resources.rukavychka
import priceninjakmp.composeapp.generated.resources.silpo
import pxToDp
import utils.Gray
import utils.GrayNavNar
import utils.Shop
import kotlin.jvm.Transient

class CardsTab(
    @Transient private val onAddCardClick: () -> Unit,
    @Transient private val writeLong: (Pair<String, Long>) -> Unit,
    @Transient private val readLong: (String) -> Long?,
    @Transient private val readString: (String) -> String?,
) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Cards"
            val icon = painterResource(Res.drawable.card)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

//        val listOfShops = mutableListOf<String>().apply {
//            Shop.entries.forEach { add(it.toString()) }
//        }
        val listOfShops = Shop.entries.map { it.toString() }
        val cardExistFromShop = listOfShops.filter {
            readLong(it) != null
        }

        println(cardExistFromShop.size)

        Column(modifier = Modifier.padding(top = pxToDp(WindowInsets.statusBars.getTop(LocalDensity.current)).dp)) {

            if (cardExistFromShop.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(210.dp)
                        .padding(horizontal = 16.dp)
                        .padding(top = 6.dp)
                        .background(color = GrayNavNar, shape = RoundedCornerShape(16.dp))
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.Center).padding(bottom = 16.dp),
                        painter = painterResource(Res.drawable.empty_card),
                        tint = Gray,
                        contentDescription = "Card"
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(top = 12.dp),
                    text = "У вас ще немає збережених карток"
                )
            } else {
                Column(
                    modifier = Modifier.scrollable(
                        state = rememberScrollState(),
                        orientation = Orientation.Vertical
                    ),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    if (readString(Shop.ATB.toString()) != null) {
                        AtbCard {

                        }
                    }

                    if (readString(Shop.SILPO.toString()) != null) {
                        SilpoCard {

                        }
                    }

                    if (readString(Shop.RUKAVYCHKA.toString()) != null) {
                        RukavychkaCard {

                        }
                    }

                }

            }

            IconButton(
                onClick = { onAddCardClick() },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 24.dp)
                    .background(color = GrayNavNar, shape = RoundedCornerShape(32.dp))
            ) {
                Icon(
                    painter = painterResource(Res.drawable.plus),
                    contentDescription = "Add card"
                )
            }

        }

    }


    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun AtbCard(onCardClick: () -> Unit) {
        Box(
            modifier = Modifier.fillMaxWidth().clickable(
                onClick = onCardClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
                .height(210.dp)
                .padding(horizontal = 16.dp)
                .padding(top = 6.dp)
                .background(shape = RoundedCornerShape(16.dp), color = Color.Black)
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(Res.drawable.atb),
                tint = Color(red = 195, green = 35, blue = 40),
                contentDescription = "Card"
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun SilpoCard(onCardClick: () -> Unit) {
        Box(
            modifier = Modifier.fillMaxWidth().clickable(
                onClick = onCardClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
                .height(210.dp)
                .padding(horizontal = 16.dp)
                .padding(top = 6.dp)
                .background(
                    shape = RoundedCornerShape(16.dp),
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(red = 33, green = 46, blue = 84),
                            Color(red = 61, green = 78, blue = 184)
                        )
                    )
                )
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(Res.drawable.silpo),
                tint = Color(red = 255, green = 133, blue = 34),
                contentDescription = "Card"
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun RukavychkaCard(onCardClick: () -> Unit) {
        Box(
            modifier = Modifier.fillMaxWidth().clickable(
                onClick = onCardClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
                .height(210.dp)
                .padding(horizontal = 16.dp)
                .padding(top = 6.dp)
                .background(
                    shape = RoundedCornerShape(16.dp),
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(red = 155, green = 14, blue = 58),
                            Color(red = 137, green = 45, blue = 74)
                        )
                    )
                )
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center).size(200.dp),
                painter = painterResource(Res.drawable.rukavychka),
                tint = Color.White,
                contentDescription = "Card"
            )
        }
    }

}