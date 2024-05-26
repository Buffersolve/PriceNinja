package home

import Gray
import GrayNavNar
import Main
import NetworkState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.home
import pxToDp

object HomeTab : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = painterResource(Res.drawable.home)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val density = LocalDensity.current
        val screenModel: HomeScreenModel = rememberScreenModel { HomeScreenModel() }

        LaunchedEffect(Unit) {
            screenModel.fetchAllData()
        }

        val chipsList = listOf("All", "ATB", "Silpo")
        val activeChip = remember { mutableStateOf(0) }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = pxToDp(WindowInsets.statusBars.getTop(density)).dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                repeat(chipsList.size) {
                    TopShopChips(text = chipsList[it], chipsList[activeChip.value]) {
                        activeChip.value = chipsList.indexOf(it)
                    }
                }
            }

            when (val data = screenModel.allData.collectAsState().value) {
                is NetworkState.Loading -> {
                    Text(text = "Loading...")
                }
                is NetworkState.Success<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    ItemsList(data.data as List<Item>)
                }
                is NetworkState.Error -> {
                    Text(text = "Error: ${data.message}")
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