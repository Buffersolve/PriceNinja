import utils.IS_SHOW_ON_BOARDING
import androidx.compose.ui.window.ComposeUIViewController
import com.liftric.kvault.KVault
import utils.IS_FIRST_START

fun MainViewController() = ComposeUIViewController {

    val store = KVault()
    val isFirstStart = store.bool(IS_FIRST_START) ?: true
    val showOnBoarding = store.bool(IS_SHOW_ON_BOARDING) ?: true

    App(
        writeString = { store.set(key = it.first, stringValue = it.second) },
        writeLong = { store.set(key = it.first, longValue = it.second) },
        writeBoolean = { store.set(key = it.first, boolValue = it.second) },
        readString = { store.string(it) },
        readLong = { store.long(it) },
        readBoolean = { store.bool(it) },
        delete = { store.deleteObject(it) },
        isFirstStart = isFirstStart,
        showOnboarding = showOnBoarding
    )
}