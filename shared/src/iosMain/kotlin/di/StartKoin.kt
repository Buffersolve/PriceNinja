package di

import org.koin.core.context.startKoin

actual fun startKoinApp() {
    startKoin {
        modules(networkModule)
    }
}