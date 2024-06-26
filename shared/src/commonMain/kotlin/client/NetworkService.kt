package client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class NetworkService(private val client: HttpClient) {

    suspend fun fetchData(url: String): String {
        val response: HttpResponse = client.get(url)
        println(response.status)
        return response.body()
    }

}