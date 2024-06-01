import utils.IS_SHOW_ON_BOARDING
import androidx.compose.ui.window.ComposeUIViewController
import com.liftric.kvault.KVault

fun MainViewController() = ComposeUIViewController {

    val store = KVault()
    val showOnBoarding = store.bool(IS_SHOW_ON_BOARDING) ?: true

    App(
        writeString = { store.set(key = it.first, stringValue = it.second) },
        writeInt = { store.set(key = it.first, intValue = it.second) },
        writeBoolean = { store.set(key = it.first, boolValue = it.second) },
        readString = { store.string(it) },
        readInt = { store.int(it) },
        readBoolean = { store.bool(it) },
        showOnboarding = showOnBoarding
    )
}