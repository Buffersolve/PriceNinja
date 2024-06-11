package di

import client.NetworkService
import io.ktor.client.*
import org.koin.dsl.module

val networkModule = module {
    single { HttpClient() }
    single { NetworkService(get()) }
}