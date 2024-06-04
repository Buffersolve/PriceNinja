import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import cards.CardScreen
import cards.CardsTab
import di.startKoinApp
import home.HomeTab
import kotlinx.serialization.Transient
import navigation.SharedScreen
import onboard.OnBoardingScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import priceninjakmp.composeapp.generated.resources.Res
import priceninjakmp.composeapp.generated.resources.atb
import priceninjakmp.composeapp.generated.resources.blyzenko
import priceninjakmp.composeapp.generated.resources.silpo
import scanner.ScannerTab
import utils.IS_FIRST_START
import utils.Shop

@Composable
@Preview
fun App(
    writeString: (Pair<String, String>) -> Unit,
    writeLong: (Pair<String, Long>) -> Unit,
    writeBoolean: (Pair<String, Boolean>) -> Unit,

    readString: (String) -> String?,
    readLong: (String) -> Long?,
    readBoolean: (String) -> Boolean?,
    delete: (String) -> Unit,

    isFirstStart: Boolean,
    showOnboarding: Boolean
) {
    try {
        startKoinApp()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    ScreenRegistry {
        register<SharedScreen.OnBoarding> { OnBoardingScreen(writeBoolean) }
        register<SharedScreen.MainScreen> { MainScreen(writeLong, writeString, readLong) }
        register<SharedScreen.HomeTab> { HomeTab }
        register<SharedScreen.ScannerTab> { ScannerTab(writeString, readLong) }
        register<SharedScreen.CardsTab> { provider ->
            CardsTab(provider.onAddCardClick, writeLong, readLong, readString, writeString)
        }
        register<SharedScreen.CardScreen> { provider ->
            CardScreen(readLong, readString, delete, provider.title)
        }

    }

    if (isFirstStart) {
        writeBoolean(Pair(IS_FIRST_START, false))
//        writeString(Pair(Shop.ATB.toString(), ""))
//        writeString(Pair(Shop.SILPO.toString(), ""))
    }

    MaterialTheme {

        val screen = if (showOnboarding) {
            OnBoardingScreen(writeBoolean = writeBoolean)
        } else {
            MainScreen(
                writeLong = writeLong,
                readLong = readLong,
                writeString = writeString
            )
        }

        Navigator(screen)
    }

}

@Composable
fun pxToDp(px: Int): Float {
    val density = LocalDensity.current.density
    return px.toFloat() / density
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShopMapper(shop: String): String {
    return when(shop) {
        Shop.ATB.name -> {
            stringResource(Res.string.atb)
        }
        Shop.SILPO.name -> {
            stringResource(Res.string.silpo)
        }
        Shop.BLYZENKO.name -> {
            stringResource(Res.string.blyzenko)
        }
        else -> {
            shop
        }
    }

}