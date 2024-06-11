package home

import utils.NetworkState
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.model.Item
import getPlatform
import home.client.NetworkServiceHelper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import utils.BASE_URL
import kotlin.jvm.Transient

class HomeScreenModel(
    @Transient private val readString: (String) -> String?
) : ScreenModel {

    private var baseUrl = "http://localhost:8080"
    private var baseUrlAndroidEmulator = "http://10.0.2.2:8080"
    init {
        baseUrl = if (getPlatform().name.contains("iOS")) "http://localhost:8080" else baseUrlAndroidEmulator
    }

    private val _allShops: MutableStateFlow<NetworkState> = MutableStateFlow(NetworkState.Loading)
    val allShops = _allShops.asStateFlow()

    fun fetchShops() = screenModelScope.launch(Dispatchers.IO) {
        baseUrl = readString(BASE_URL) ?: baseUrl

        val networkService = NetworkServiceHelper().getNetworkServiceClient()
        try {
            val data = networkService.fetchData(baseUrl + SHOPS_URL)
            val shopsJson: List<String> = Json.decodeFromString(data)
            _allShops.value = NetworkState.Success(shopsJson)
            println(shopsJson)
        } catch (e: Exception) {
            _allShops.value = NetworkState.Error(true, e.message)
            e.printStackTrace()
        }
    }

    private val _allData: MutableStateFlow<NetworkState> = MutableStateFlow(NetworkState.Loading)
    val allData = _allData.asStateFlow()

    fun fetchAllData() = screenModelScope.launch(Dispatchers.IO) {
        baseUrl = readString(BASE_URL) ?: baseUrl

        val networkService = NetworkServiceHelper().getNetworkServiceClient()
        try {
            val data = networkService.fetchData(baseUrl + ALL_DATA_URL)
            val allDataJson: List<Item> = Json.decodeFromString(data)
            _allData.value = NetworkState.Success(allDataJson)
            println(allDataJson)
        } catch (e: CancellationException) {
            _allData.value = NetworkState.Error(true, e.message)
            e.printStackTrace()
        } catch (e: Exception) {
            _allData.value = NetworkState.Error(true, e.message)
            this.cancel()
            e.printStackTrace()
        }
    }

    private val _silpoData: MutableStateFlow<NetworkState> = MutableStateFlow(NetworkState.Loading)
    val silpoData = _silpoData.asStateFlow()

    fun fetchSilpoData() = screenModelScope.launch(Dispatchers.IO) {
        baseUrl = readString(BASE_URL) ?: baseUrl

        val networkService = NetworkServiceHelper().getNetworkServiceClient()
        try {
            val data = networkService.fetchData(baseUrl + SILPO_URL)
            val silpoJson: List<Item> = Json.decodeFromString(data)
            _silpoData.value = NetworkState.Success(silpoJson)
            println(silpoJson)
        } catch (e: CancellationException) {
            _silpoData.value = NetworkState.Error(true, e.message)
            e.printStackTrace()
        } catch (e: Exception) {
            _silpoData.value = NetworkState.Error(true, e.message)
            this.cancel()
            e.printStackTrace()
        }
    }

    private val _atbData: MutableStateFlow<NetworkState> = MutableStateFlow(NetworkState.Loading)
    val atbData = _atbData.asStateFlow()

    fun fetchAtbData() = screenModelScope.launch(Dispatchers.IO) {
        baseUrl = readString(BASE_URL) ?: baseUrl

        val networkService = NetworkServiceHelper().getNetworkServiceClient()
        try {
            val data = networkService.fetchData(baseUrl + ATB_URL)
            val atbJson: List<Item> = Json.decodeFromString(data)
            _atbData.value = NetworkState.Success(atbJson)
            println(atbJson)
        } catch (e: CancellationException) {
            _atbData.value = NetworkState.Error(true, e.message)
            e.printStackTrace()
        } catch (e: Exception) {
            _atbData.value = NetworkState.Error(true, e.message)
            this.cancel()
            e.printStackTrace()
        }
    }

    private val _blyzenkoData: MutableStateFlow<NetworkState> = MutableStateFlow(NetworkState.Loading)
    val blyzenkoData = _blyzenkoData.asStateFlow()

    fun fetchBlyzenkoData() = screenModelScope.launch(Dispatchers.IO) {
        baseUrl = readString(BASE_URL) ?: baseUrl

        val networkService = NetworkServiceHelper().getNetworkServiceClient()
        try {
            val data = networkService.fetchData(baseUrl + BLYZENKO_URL)
            val blyzenkoJson: List<Item> = Json.decodeFromString(data)
            _blyzenkoData.value = NetworkState.Success(blyzenkoJson)
        } catch (e: CancellationException) {
            _blyzenkoData.value = NetworkState.Error(true, e.message)
            e.printStackTrace()
        } catch (e: Exception) {
            _blyzenkoData.value = NetworkState.Error(true, e.message)
            this.cancel()
            e.printStackTrace()
        }
    }

    companion object {

//        private val BASE_URL = if (getPlatform().name.contains("iOS")) "http://localhost:8080" else "http://10.0.2.2:8080"
//        private val BASE_URL = "http://localhost:8080"
//        private val BASE_URL = "http://192.168.0.119:8080"

        private const val SHOPS_URL = "/shops"
        private const val ALL_DATA_URL = "/data"
        private const val SILPO_URL = "/data/silpo"
        private const val ATB_URL = "/data/atb"
        private const val BLYZENKO_URL = "/data/blyzenko"
    }

}