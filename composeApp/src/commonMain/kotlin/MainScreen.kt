import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cards.CardsTab
import shop.HomeTab

object MainScreen : Screen {

    @Composable
    override fun Content() {
        TabNavigator(tab = HomeTab) { tabNavigator ->
//            CurrentTab()
            val density = LocalDensity.current


            Scaffold(content = {
                CurrentTab()

            }, bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(bottom = pxToDp(WindowInsets.navigationBars.getBottom(density)).dp)
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(36.dp)
                            )
                            .padding(horizontal = 24.dp, vertical = 4.dp)
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                    ) {
                        TabNavigationItem(HomeTab, tabNavigator)
                        TabNavigationItem(CardsTab, tabNavigator)
                    }
                }

            })


        }
    }

    @Composable
    private fun TabNavigationItem(screenTab: Tab, tabNavigator: TabNavigator) {
//        val tabNavigator = LocalTabNavigator.current
        val selected = tabNavigator.current == screenTab
        val interactionSource = remember { MutableInteractionSource() }

        var iconColor by rememberSaveable { mutableIntStateOf(Color.Gray.toArgb()) }
        var textColor by rememberSaveable { mutableIntStateOf(Color.Gray.toArgb()) }

        iconColor = if (selected) Color.Black.toArgb() else Color.Gray.toArgb()
        textColor = if (selected) Color.Black.toArgb() else Color.Gray.toArgb()

        BoxWithConstraints(
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

}