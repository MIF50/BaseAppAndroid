package com.mif50.baseapp.data.network.response

import com.mif50.baseapp.data.network.ModelResponse
import com.mif50.baseapp.domain.model.Transaction
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DummySuccessResponse(
        @Json(name = "transation")
        val transaction: List<Transaction>
) : ModelResponse()