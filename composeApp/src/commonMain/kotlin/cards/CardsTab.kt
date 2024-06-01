package cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.card
import priceninjakmp.composeapp.generated.resources.empty_card
import priceninjakmp.composeapp.generated.resources.plus
import pxToDp
import utils.Gray
import utils.GrayNavNar
import utils.Main
import kotlin.jvm.Transient

class CardsTab(
    @Transient private val onAddCardClick: () -> Unit
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

        Column(modifier = Modifier.padding(top = pxToDp(WindowInsets.statusBars.getTop(LocalDensity.current)).dp)) {
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
                    .padding(top = 12.dp, bottom = 24.dp), text = "У вас ще немає збережених карток"
            )

            IconButton(
                onClick = { onAddCardClick() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .background(color = GrayNavNar, shape = RoundedCornerShape(32.dp))
            ) {
                Icon(
                    painter = painterResource(Res.drawable.plus),
                    contentDescription = "Add card"
                )
            }

        }

    }


}