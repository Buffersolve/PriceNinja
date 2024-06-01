package onboard

import utils.Gray
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.onboarding_1
import priceninjakmp.composeapp.generated.resources.onboarding_2
import priceninjakmp.composeapp.generated.resources.onboarding_3

data class OnBoardingTab(
    private val image: Int,
    private val title: String,
    private val subTitle: String,
) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "First"
            val icon = null

            return TabOptions(
                index = 0u,
                title = title,
                icon = icon
            )
        }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        val currentImageRes = when(image) {
            1 -> Res.drawable.onboarding_1
            2 -> Res.drawable.onboarding_2
            3 -> Res.drawable.onboarding_3
            else -> Res.drawable.onboarding_1
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                    .align(CenterHorizontally).weight(2f),
                painter = painterResource(currentImageRes),
                contentDescription = "Onboarding 1"
            )

            Text(
                modifier = Modifier.align(CenterHorizontally).padding(horizontal = 50.dp)
                    .padding(bottom = 12.dp),
                text = title,
                style = TextStyle(
                    fontSize = 32.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier.align(CenterHorizontally).padding(horizontal = 60.dp)
                    .padding(bottom = 84.dp + if (image == 3) 32.dp else 0.dp),
                text = subTitle,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Gray,
                    fontWeight = FontWeight.Normal
                )
            )
        }

    }

}