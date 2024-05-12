import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import cafe.adriel.voyager.navigator.Navigator
import di.startKoinApp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.KoinApplication

@Composable
@Preview
fun App() {
    startKoinApp()
    MaterialTheme {
        Navigator(MainScreen)
    }

}

@Composable
fun pxToDp(px: Int): Float {
    val density = LocalDensity.current.density
    return px.toFloat() / density
}