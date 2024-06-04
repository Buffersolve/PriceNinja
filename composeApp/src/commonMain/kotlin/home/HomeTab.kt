package home

import ShopMapper
import utils.Gray
import utils.GrayNavNar
import utils.Main
import utils.NetworkState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.model.Item
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.all
import priceninjakmp.composeapp.generated.resources.home
import priceninjakmp.composeapp.generated.resources.loading
import pxToDp
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

            when (activeChip.value) {
                0 -> screenModel.fetchAllData()
                1 -> screenModel.fetchAtbData()
                2 -> screenModel.fetchSilpoData()
                3 -> screenModel.fetchBlyzenkoData()
            }

            when (val data = when (activeChip.value) {
                0 -> screenModel.allData.collectAsState()
                1 -> screenModel.atbData.collectAsState()
                2 -> screenModel.silpoData.collectAsState()
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
                    ItemsList(data.data as List<Item>)
                }

                is NetworkState.Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(text = "Error: ${data.message}", modifier = Modifier.align(Alignment.Center).padding(bottom = 100.dp))
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