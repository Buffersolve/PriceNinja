package ua.priceninja

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.liftric.kvault.KVault
import utils.IS_SHOW_ON_BOARDING
import utils.settings

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            )
        )

        val store = KVault(this, settings)
        val showOnBoarding = store.bool(IS_SHOW_ON_BOARDING) ?: true

        setContent {
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
    }
}