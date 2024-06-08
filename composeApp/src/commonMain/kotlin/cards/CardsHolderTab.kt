package cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.card
import priceninjakmp.composeapp.generated.resources.cards
import kotlin.jvm.Transient

class CardsHolderTab(
    @Transient private val onAddCardClick: () -> Unit,
    @Transient private val writeLong: (Pair<String, Long>) -> Unit,
    @Transient private val readLong: (String) -> Long?,
    @Transient private val readString: (String) -> String?,
    @Transient private val writeString: (Pair<String, String>) -> Unit,
) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.cards)
            val icon = painterResource(Res.drawable.card)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {

        Navigator(
            CardsScreen(
                onAddCardClick = onAddCardClick,
                writeLong = writeLong,
                readLong = readLong,
                readString = readString,
                writeString = writeString
            )
        ) { navigator ->
            SlideTransition(navigator)
        }

    }


}