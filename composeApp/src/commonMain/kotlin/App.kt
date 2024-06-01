import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import cafe.adriel.voyager.navigator.Navigator
import di.startKoinApp
import onboard.OnBoardingScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    writeString: (Pair<String, String>) -> Unit,
    writeInt: (Pair<String, Int>) -> Unit,
    writeBoolean: (Pair<String, Boolean>) -> Unit,

    readString: (String) -> String?,
    readInt: (String) -> Int?,
    readBoolean: (String) -> Boolean?,

    showOnboarding: Boolean
) {
    startKoinApp()

    MaterialTheme {
        Navigator(if (showOnboarding) OnBoardingScreen(writeBoolean = writeBoolean) else MainScreen)
    }

}

@Composable
fun pxToDp(px: Int): Float {
    val density = LocalDensity.current.density
    return px.toFloat() / density
}