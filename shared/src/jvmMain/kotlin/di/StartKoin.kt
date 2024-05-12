package di

import org.koin.core.context.GlobalContext.startKoin

actual fun startKoinApp() {
    startKoin {
        modules(networkModule, databaseModule, daoModule)
    }
}