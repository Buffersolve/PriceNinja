package scanner

import ShopMapper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.publicvalue.multiplatform.qrcode.CodeType
import org.publicvalue.multiplatform.qrcode.ScannerWithPermissions
import priceninja.composeapp.generated.resources.Res
import priceninja.composeapp.generated.resources.atb_bage
import priceninja.composeapp.generated.resources.blyzenko_bage
import priceninja.composeapp.generated.resources.card
import priceninja.composeapp.generated.resources.scan
import priceninja.composeapp.generated.resources.silpo_bage
import utils.Shop
import kotlin.jvm.Transient

class ScannerTab(
    @Transient private val writeString: (Pair<String, String>) -> Unit,
    @Transient private val readLong: (String) -> Long?,
) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Scanner"
            val icon = painterResource(Res.drawable.scan)

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

        var scannedResult by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(top = 100.dp, bottom = 150.dp, start = 20.dp, end = 20.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        ) {

            ScannerWithPermissions(
                permissionText = "",
                openSettingsLabel = "",
                onScanned = {
                    scannedResult = it; showDialog = true; true
                },
                types = CodeType.entries.map { it }
            )
            CameraScannerDecoration()

        }

        if (showDialog) {
            ChooseShopDialog(
                scannedRes = scannedResult,
                writeString = writeString,
                onDismissRequest = { showDialog = false }
            )
        }


    }

    @Composable
    fun CameraScannerDecoration() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxHeight().width(24.dp)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .align(Alignment.TopStart)
            )
            Box(
                modifier = Modifier.fillMaxHeight().width(24.dp)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .align(Alignment.BottomEnd)
            )
            Box(
                modifier = Modifier.fillMaxWidth().height(250.dp).padding(horizontal = 24.dp)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .align(Alignment.TopStart)
            )
            Box(
                modifier = Modifier.fillMaxWidth().height(250.dp).padding(horizontal = 24.dp)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .align(Alignment.BottomEnd)
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun ChooseShopDialog(
        scannedRes: String,
        writeString: (Pair<String, String>) -> Unit,
        onDismissRequest: () -> Unit
    ) {
//        val navigator = LocalNavigator.currentOrThrow
        val listOfShops = Shop.entries.map { it.toString() }

        Dialog(onDismissRequest = { onDismissRequest() }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column {
                    repeat(listOfShops.size) {
                        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {

                            TextButton(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                onClick = {
                                    writeString(listOfShops[it] to scannedRes)
                                    onDismissRequest()
                                }) {
                                Text(
                                    text = ShopMapper(listOfShops[it]),
                                    color = Color.Black
                                )
                            }

                            Image(
                                painter = when (listOfShops[it]) {
                                    Shop.SILPO.name -> painterResource(Res.drawable.silpo_bage)
                                    Shop.ATB.name -> painterResource(Res.drawable.atb_bage)
                                    Shop.BLYZENKO.name -> painterResource(Res.drawable.blyzenko_bage)
                                    else -> painterResource(Res.drawable.card)
                                },
                                contentDescription = "Item Bage",
                                modifier = Modifier.size(64.dp).align(Alignment.CenterVertically)
                            )


                        }
                    }
                }
            }
        }
    }

}