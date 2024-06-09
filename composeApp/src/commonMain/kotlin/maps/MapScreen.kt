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
    private val shop: Int,
) : Screen {

    @Composable
    override fun Content() {
        val uri = when(shop) {
            0 -> "https://www.google.com/maps/search/%D0%A1%D1%96%D0%BB%D1%8C%D0%BF%D0%BE/@49.8322807,24.0028951,13z?entry=ttu"
            1 -> "https://www.google.com.ua/maps/search/%D0%90%D1%82%D0%B1/@49.8394846,24.0050831,13.75z?entry=ttu"
            2 -> "https://www.google.com.ua/maps/search/%D0%91%D0%BB%D0%B8%D0%B7%D0%B5%D0%BD%D1%8C%D0%BA%D0%BE/@49.8433557,24.0130654,14z?entry=ttu"
            else -> "https://www.google.com/maps/search/%D0%A1%D1%96%D0%BB%D1%8C%D0%BF%D0%BE/@49.8322807,24.0028951,13z?entry=ttu"
        }
        val state = rememberWebViewState(uri)

        WebView(
            state = state, modifier = Modifier.fillMaxSize().padding(
                top = pxToDp(WindowInsets.statusBars.getTop(LocalDensity.current)).dp,
                bottom = pxToDp(WindowInsets.navigationBars.getBottom(LocalDensity.current)).dp
            )
        )
    }

}