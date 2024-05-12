package home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.model.Item
import pxToDp

object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = rememberVectorPainter(Icons.Default.Home)

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

        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                    .padding(top = pxToDp(WindowInsets.statusBars.getTop(density)).dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TopShopChips(text = "All")
                TopShopChips(text = "Silpo")
                TopShopChips(text = "ATB")
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

//            ItemsList()
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopShopChips(text: String) {
    Chip(
        modifier = Modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.Black),
        onClick = {

        }
    ) {
        Text(text = text, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 4.dp))
    }
}