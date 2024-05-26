package scanner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.publicvalue.multiplatform.qrcode.CodeType
import org.publicvalue.multiplatform.qrcode.ScannerWithPermissions
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.scan

object ScannerTab : Tab {

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

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(top = 100.dp, bottom = 150.dp, start = 20.dp, end = 20.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        ) {

            ScannerWithPermissions(
                permissionText = "",
                openSettingsLabel = "",
                onScanned = {

                    println(it); false

                }, types = listOf(CodeType.QR)
            )

            Box(
                modifier = Modifier.size(210.dp).align(Alignment.Center)
                    .border(4.dp, Color.Black, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {

            }

        }

    }


}