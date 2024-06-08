package home

import ShopMapper
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.model.Item
import maps.MapScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.all
import priceninjakmp.composeapp.generated.resources.filter
import priceninjakmp.composeapp.generated.resources.home
import priceninjakmp.composeapp.generated.resources.loading
import pxToDp
import utils.Gray
import utils.GrayNavNar
import utils.Main
import utils.NetworkState
import utils.Shop

object HomeTab : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.home)
            val icon = painterResource(Res.drawable.home)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val density = LocalDensity.current
        val screenModel: HomeScreenModel = rememberScreenModel { HomeScreenModel() }
        val navigator = LocalNavigator.currentOrThrow.parent

        var dropDownState by remember { mutableStateOf(false) }

        val chipsList = mutableListOf(stringResource(Res.string.all))
        chipsList.addAll(Shop.entries.map { ShopMapper(it.name) })

        val activeChip = remember { mutableStateOf(0) }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = pxToDp(WindowInsets.statusBars.getTop(density)).dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                repeat(chipsList.size) {
                    TopShopChips(text = chipsList[it], chipsList[activeChip.value]) {
                        activeChip.value = chipsList.indexOf(it)
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.align(Alignment.TopEnd).clickable(
                        onClick = {
                            dropDownState = !dropDownState
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.filter),
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )
                    Icon(
                        imageVector = vectorResource(Res.drawable.filter),
                        contentDescription = "Filter",
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                }
                if (dropDownState) {
                    Box(modifier = Modifier.wrapContentWidth().align(Alignment.BottomEnd)) {
                        DropDown(onDismissRequest = { dropDownState = false })
                    }
                }
            }

            when (activeChip.value) {
                0 -> screenModel.fetchAllData()
                1 -> screenModel.fetchSilpoData()
                2 -> screenModel.fetchAtbData()
                3 -> screenModel.fetchBlyzenkoData()
            }

            when (val data = when (activeChip.value) {
                0 -> screenModel.allData.collectAsState()
                1 -> screenModel.silpoData.collectAsState()
                2 -> screenModel.atbData.collectAsState()
                3 -> screenModel.blyzenkoData.collectAsState()
                else -> screenModel.allData.collectAsState()
            }.value) {
                is NetworkState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = stringResource(Res.string.loading),
                            modifier = Modifier.align(Alignment.Center).padding(bottom = 100.dp)
                        )
                    }
                }

                is NetworkState.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    ItemsList(data.data as List<Item>) {
                        navigator?.push(MapScreen(it.shop))
                    }
                }

                is NetworkState.Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Error: ${data.message}",
                            modifier = Modifier.align(Alignment.Center).padding(bottom = 100.dp)
                        )
                    }
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopShopChips(text: String, activeChip: String, chipClickCallback: (String) -> Unit) {
    Chip(
        modifier = Modifier,
        shape = RoundedCornerShape(16.dp),
        onClick = {
            chipClickCallback(text)
        },
        colors = ChipDefaults.chipColors(
            backgroundColor = if (text == activeChip) Main else GrayNavNar,
            contentColor = if (text == activeChip) Color.White else Gray
        ),

        ) {
        Text(text = text, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 4.dp))
    }
}

@Composable
fun DropDown(onDismissRequest: () -> Unit) {
    var selected by rememberSaveable { mutableStateOf(0) }

    DropdownMenu(
        modifier = Modifier.clip(RoundedCornerShape(24.dp))
            .background(Color.White, shape = RoundedCornerShape(24.dp)).padding(12.dp),
        expanded = true,
        onDismissRequest = onDismissRequest
    ) {
        DropDownTextItem(selected == 0, "Звичайна") {
            selected = 0
        }
        DropDownTextItem(selected == 1, "Від найдешевшого") {
            selected = 1
        }
        DropDownTextItem(selected == 2, "Від найдорожчого") {
            selected = 2
        }
    }
}

@Composable
fun DropDownTextItem(isSelected: Boolean, text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().background(if (isSelected) GrayNavNar else Color.White)
            .clickable(onClick = onClick)
    ) {
        Text(text = text, modifier = Modifier.padding(4.dp))
    }

}