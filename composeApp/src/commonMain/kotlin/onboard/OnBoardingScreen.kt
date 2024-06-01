package onboard

import utils.GrayNavNar
import utils.IS_SHOW_ON_BOARDING
import utils.Main
import MainScreen
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import kotlinx.coroutines.launch
import pxToDp

class OnBoardingScreen(
    private val writeBoolean: (Pair<String, Boolean>) -> Unit,
) : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val tabsCollection = listOf(
            OnBoardingTab(
                1,
                "5 супермаркетів в 1 додатку",
                "Переглядайте знижки на улюблені товари в одному додатку"
            ),
            OnBoardingTab(
                2,
                "Скануйте знижкові карти",
                "Скануйте та добавляйте карти в один додаток"
            ),
            OnBoardingTab(
                3,
                "Гаманець знижок",
                "Зберігайте знижкові картки супермаркетів в одному додатку"
            )
        )
        val pagerState = rememberPagerState(pageCount = { tabsCollection.count() }, initialPage = 0)
        val scope = rememberCoroutineScope()

        TabNavigator(tabsCollection[0], tabDisposable = {
            TabDisposable(navigator = it, tabs = tabsCollection)
        }) { tabNavigator ->

            LaunchedEffect(pagerState.currentPage) {
                tabNavigator.current = tabsCollection[pagerState.currentPage]
            }

            Column(modifier = Modifier.fillMaxSize()) {

                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = pxToDp(WindowInsets.statusBars.getTop(LocalDensity.current)).dp),
                    state = pagerState,
                    verticalAlignment = Alignment.Top,
                    userScrollEnabled = true,
                ) { tabIndex ->
                    tabsCollection[tabIndex].Content()
                }

                Button(
                    onClick = {
                        if (pagerState.currentPage < tabsCollection.size - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            writeBoolean(IS_SHOW_ON_BOARDING to false)
                            navigator.push(MainScreen)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Main),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 16.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                ) {
                    Text(modifier = Modifier.padding(8.dp), text = "Next", color = Color.White)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(
                            bottom = pxToDp(
                                WindowInsets.navigationBars.getBottom(LocalDensity.current)
                            ).dp + 8.dp
                        )
                ) {
                    DotsIndicator(
                        Modifier.align(Alignment.Center),
                        tabsCollection.size,
                        pagerState.currentPage
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                    }
                }

            }


        }

    }

    @Composable
    fun DotsIndicator(modifier: Modifier = Modifier, totalDots: Int, selectedIndex: Int, dotClickCallBack: (Int) -> Unit = {}) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(totalDots) { index ->
                val color = if (index == selectedIndex) {
                    Main
                } else {
                    GrayNavNar
                }
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(height = 6.dp, width = 38.dp)
                        .background(color, shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            dotClickCallBack(index)
                        }
                )
            }

        }
    }

}