package navigation

import cafe.adriel.voyager.core.registry.ScreenProvider
import kotlin.jvm.Transient

sealed class SharedScreen : ScreenProvider {
    data object OnBoarding : SharedScreen()
    data object MainScreen : SharedScreen()
    data object HomeTab : SharedScreen()
    data object ScannerTab : SharedScreen()
    data class CardsTab(
        @Transient val onAddCardClick: () -> Unit,
    ) : SharedScreen()
    data class CardScreen (
        val title: String
    ) : SharedScreen()
}