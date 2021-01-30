package com.mif50.baseapp.helper.network

import com.mif50.baseapp.helper.network.StateNetworkException.DEFAULT_EXCEPTION
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


object StateNetworkException {
    const val DEFAULT_EXCEPTION = 0
    const val CONNECTION_EXCEPTION = -1
    const val NETWORK_EXCEPTION = -2
}

@JsonClass(generateAdapter = true)
data class NetworkError(
    val code: Int = DEFAULT_EXCEPTION,

    @Json(name = "response")
    val response: Int = -1,

    @Json(name = "message")
    val message: String = "Something went wrong",
)