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
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import navigation.SharedScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.atb
import priceninjakmp.composeapp.generated.resources.blyzenko
import priceninjakmp.composeapp.generated.resources.empty_card
import priceninjakmp.composeapp.generated.resources.plus
import priceninjakmp.composeapp.generated.resources.silpo
import priceninjakmp.composeapp.generated.resources.you_dont_have_saved_cards
import pxToDp
import utils.Gray
import utils.GrayNavNar
import utils.Shop
import kotlin.jvm.Transient

class CardsScreen(
    @Transient private val onAddCardClick: () -> Unit,
    @Transient private val writeLong: (Pair<String, Long>) -> Unit,
    @Transient private val readLong: (String) -> Long?,
    @Transient private val readString: (String) -> String?,
    @Transient private val writeString: (Pair<String, String>) -> Unit,
) : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

//        val listOfShops = mutableListOf<String>().apply {
//            Shop.entries.forEach { add(it.toString()) }
//        }
        val navigator = LocalNavigator.currentOrThrow
        val listOfShops = Shop.entries.map { it.toString() }
        val cardExistFromShop = listOfShops.filter {
            readLong(it) != null
        }

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
                    text = stringResource(Res.string.you_dont_have_saved_cards)
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
                            navigator.push(ScreenRegistry.get(SharedScreen.CardScreen(Shop.ATB.toString())))
                        }
                    }

                    if (readString(Shop.SILPO.toString()) != null) {
                        SilpoCard {
                            navigator.push(ScreenRegistry.get(SharedScreen.CardScreen(Shop.SILPO.toString())))
                        }
                    }

                    if (readString(Shop.BLYZENKO.toString()) != null) {
                        BlyzenkoCard {
                            navigator.push(ScreenRegistry.get(SharedScreen.CardScreen(Shop.BLYZENKO.toString())))
                        }
                    }

//                    if (readString(Shop.RUKAVYCHKA.toString()) != null) {
//                        RukavychkaCard {
//
//                        }
//                    }


                }

            }

            IconButton(
                onClick = onAddCardClick ,
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

//    @OptIn(ExperimentalResourceApi::class)
//    @Composable
//    fun RukavychkaCard(onCardClick: () -> Unit) {
//        Box(
//            modifier = Modifier.fillMaxWidth().clickable(
//                onClick = onCardClick,
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null
//            )
//                .height(210.dp)
//                .padding(horizontal = 16.dp)
//                .padding(top = 6.dp)
//                .background(
//                    shape = RoundedCornerShape(16.dp),
//                    brush = Brush.horizontalGradient(
//                        listOf(
//                            Color(red = 155, green = 14, blue = 58),
//                            Color(red = 137, green = 45, blue = 74)
//                        )
//                    )
//                )
//        ) {
//            Icon(
//                modifier = Modifier.align(Alignment.Center).size(200.dp),
//                painter = painterResource(Res.drawable.rukavychka),
//                tint = Color.White,
//                contentDescription = "Card"
//            )
//        }
//    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun BlyzenkoCard(onCardClick: () -> Unit) {
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
                            Color(red = 33, green = 47, blue = 50),
                            Color(red = 45, green = 64, blue = 68)
                        )
                    )
                )
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center).size(200.dp),
                painter = painterResource(Res.drawable.blyzenko),
                tint = Color(red = 143, green = 200, blue = 91),
                contentDescription = "Card"
            )
        }
    }

}