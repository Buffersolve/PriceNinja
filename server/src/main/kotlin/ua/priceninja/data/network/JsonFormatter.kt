package ua.priceninja.data.network

import kotlinx.serialization.json.Json

object JsonFormatter {
    val format = Json {
        ignoreUnknownKeys = true
    }
}