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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Item
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import priceninja.composeapp.generated.resources.Res
import priceninja.composeapp.generated.resources.all
import priceninja.composeapp.generated.resources.filter
import priceninja.composeapp.generated.resources.from_cheapest
import priceninja.composeapp.generated.resources.from_expensive
import priceninja.composeapp.generated.resources.loading
import priceninja.composeapp.generated.resources.regular
import product.ProductScreen
import pxToDp
import utils.Gray
import utils.GrayNavNar
import utils.Main
import utils.NetworkState
import utils.Shop
import kotlin.jvm.Transient

class HomeScreen(
    @Transient private val readString: (String) -> String?
) : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val density = LocalDensity.current
        val screenModel: HomeScreenModel = rememberScreenModel { HomeScreenModel(readString) }
        val navigator = LocalNavigator.currentOrThrow

        var dropDownState by remember { mutableStateOf(false) }

        val chipsList = mutableListOf(stringResource(Res.string.all))
        chipsList.addAll(Shop.entries.map { ShopMapper(it.name) })

        var activeChip by remember { mutableStateOf(0) }
        var filterState by remember { mutableStateOf(0) }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = pxToDp(WindowInsets.statusBars.getTop(density)).dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                repeat(chipsList.size) {
                    TopShopChips(text = chipsList[it], chipsList[activeChip]) {
                        activeChip = chipsList.indexOf(it)
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)) {
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
                        DropDown(onDismissRequest = { dropDownState = false }) {
                            filterState = it
                        }
                    }
                }
            }

            when (activeChip) {
                0 -> screenModel.fetchAllData()
                1 -> screenModel.fetchSilpoData()
                2 -> screenModel.fetchAtbData()
                3 -> screenModel.fetchBlyzenkoData()
            }

            when (val data = when (activeChip) {
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
                    val items: MutableState<List<Item>> = mutableStateOf(data.data as List<Item>)

                    val sortedItems = when (filterState) {
                        1 -> items.value.sortedBy { it.discountPrice }
                        2 -> items.value.sortedByDescending { it.discountPrice }
                        else -> items.value
                    }
                    items.value = sortedItems

                    ItemsList(items.value) {
//                        navigator?.push(MapScreen(it.shop))
                        navigator.push(ProductScreen(
                            it.image,
                            it.name,
                            it.oldPrice,
                            it.discountPrice,
                            it.shop
                        ))
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

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DropDown(onDismissRequest: () -> Unit, onSelect: (Int) -> Unit) {
    var selected by rememberSaveable { mutableStateOf(0) }
    LaunchedEffect(selected) {
        onSelect(selected)
    }

    DropdownMenu(
        modifier = Modifier.clip(RoundedCornerShape(24.dp))
            .background(Color.White, shape = RoundedCornerShape(24.dp)).padding(vertical = 8.dp),
        expanded = true,
        onDismissRequest = onDismissRequest
    ) {
        DropDownTextItem(selected == 0, stringResource(Res.string.regular)) {
            selected = 0
        }
        DropDownTextItem(selected == 1, stringResource(Res.string.from_cheapest)) {
            selected = 1
        }
        DropDownTextItem(selected == 2, stringResource(Res.string.from_expensive)) {
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
        Text(text = text, modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp))
    }

}