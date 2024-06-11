package utils

sealed class NetworkState {
    data object Loading : NetworkState()
    data class Error(val isError: Boolean, val message: String?) : NetworkState()
    data class Success<T>(val data: T) : NetworkState()
}

