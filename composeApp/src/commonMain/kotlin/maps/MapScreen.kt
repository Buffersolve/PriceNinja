package maps

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import pxToDp

class MapScreen(
    private val link: String,
) : Screen {

    @Composable
    override fun Content() {
        val state = rememberWebViewState("https://www.google.com/maps/search/%D0%A1%D1%96%D0%BB%D1%8C%D0%BF%D0%BE/@49.8322807,24.0028951,13z?entry=ttu")

        WebView(
            state = state, modifier = Modifier.fillMaxSize().padding(
                top = pxToDp(WindowInsets.statusBars.getTop(LocalDensity.current)).dp,
                bottom = pxToDp(WindowInsets.navigationBars.getBottom(LocalDensity.current)).dp
            )
        )
    }

}