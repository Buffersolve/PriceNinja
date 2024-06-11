package home.client

import client.NetworkService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NetworkServiceHelper : KoinComponent {
    private val networkService : NetworkService by inject()
    fun getNetworkServiceClient() : NetworkService = networkService
}