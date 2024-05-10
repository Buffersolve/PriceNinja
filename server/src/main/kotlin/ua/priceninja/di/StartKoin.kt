package ua.priceninja.di

import org.koin.core.context.GlobalContext.startKoin

fun startKoinApp() {
    startKoin {
        modules(networkModule, databaseModule, daoModule)
    }
}