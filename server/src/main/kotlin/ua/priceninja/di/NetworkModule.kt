package ua.priceninja.di

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import org.koin.dsl.module
import ua.priceninja.data.network.client.NetworkService

val networkModule = module {
    single { HttpClient(OkHttp) }
    single { NetworkService(get()) }
}