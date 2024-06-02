import utils.Gray
import utils.GrayNavNar
import utils.Main
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cards.CardsTab
import home.HomeTab
import navigation.SharedScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.scan
import scanner.ScannerTab
import kotlin.jvm.Transient

class MainScreen(
    @Transient private val writeLong: (Pair<String, Long>) -> Unit,
    @Transient private val writeString: (Pair<String, String>) -> Unit,
    @Transient private val readLong: (String) -> Long?,
) : Screen {

    @Composable
    override fun Content() {
//        val homeTab = rememberScreen(SharedScreen.HomeTab) as Tab
//        val scannerTab = rememberScreen(SharedScreen.ScannerTab) as Tab


        TabNavigator(tab = HomeTab) { tabNavigator ->
            val density = LocalDensity.current

//            val cardsTab = rememberScreen(SharedScreen.CardsTab {
//                tabNavigator.current = scannerTab
//            }) as Tab

            Scaffold(content = {
                CurrentTab()
            }, bottomBar = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(
                                bottom = pxToDp(WindowInsets.navigationBars.getBottom(density)).dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                            .background(color = GrayNavNar, shape = RoundedCornerShape(64.dp))
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TabNavigationItem(
                            ScreenRegistry.get(SharedScreen.HomeTab) as Tab,
                            tabNavigator
                        )
                        ScannerNavItem(
                            ScreenRegistry.get(SharedScreen.ScannerTab) as Tab,
                            tabNavigator
                        )
                        TabNavigationItem(ScreenRegistry.get(SharedScreen.CardsTab {
                            tabNavigator.current = ScannerTab(writeString, readLong)
                        }) as Tab, tabNavigator)
                    }
                }

            })
        }
    }

    @Composable
    private fun TabNavigationItem(screenTab: Tab, tabNavigator: TabNavigator) {
        val selected = tabNavigator.current == screenTab
        val interactionSource = remember { MutableInteractionSource() }

        var iconColor by rememberSaveable { mutableIntStateOf(Gray.toArgb()) }
        var textColor by rememberSaveable { mutableIntStateOf(Gray.toArgb()) }

        iconColor = if (selected) Main.toArgb() else Gray.toArgb()
        textColor = if (selected) Main.toArgb() else Gray.toArgb()

        Box(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        tabNavigator.current = screenTab
                    },
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                screenTab.options.icon?.let {
                    Icon(
                        painter = it,
                        contentDescription = screenTab.options.title,
                        tint = Color(iconColor)
                    )
                }
                Text(
                    text = screenTab.options.title,
                    color = Color(textColor),
                    maxLines = 1
                )
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun ScannerNavItem(screenTab: Tab, tabNavigator: TabNavigator) {
        Box(
            modifier = Modifier.size(56.dp).background(Main, shape = RoundedCornerShape(28.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        tabNavigator.current = screenTab
                    })
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.scan),
                contentDescription = "Scan",
                tint = Color.White,
                modifier = Modifier.size(24.dp).align(Alignment.Center)
            )


        }
    }


}