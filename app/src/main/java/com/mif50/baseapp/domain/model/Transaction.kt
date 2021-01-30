package com.mif50.baseapp.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Transaction(
        @Json(name = "id")
        val id: Int = 0,

        @Json(name = "amount")
        val amount: Double = 0.0,

        @Json(name = "recipient_id")
        val recipientId: String = "",

        @Json(name = "date")
        val date: String = ""
)