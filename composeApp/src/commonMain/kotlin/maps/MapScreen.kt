package maps

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import pxToDp

class MapScreen(
    private val shop: String,
) : Screen {

    @Composable
    override fun Content() {
        val state = rememberWebViewState("https://www.google.com/maps/search/$shop/@49.8435677,24.0118612,14z?entry=ttu")

        WebView(
            state = state, modifier = Modifier.fillMaxSize().padding(
                top = pxToDp(WindowInsets.statusBars.getTop(LocalDensity.current)).dp,
                bottom = pxToDp(WindowInsets.navigationBars.getBottom(LocalDensity.current)).dp
            )
        )

    }

}