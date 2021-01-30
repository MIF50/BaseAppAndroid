package com.mif50.baseapp.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class ModelResponse (

    @Json(name = "response")
    val  _response: Int? = null,

    @Json(name = "message")
    val _message: String? = null
){
    val response get() = _response ?: 500
    val message get() = _message  ?: "our servers are busy, we will fixed it soon."
}