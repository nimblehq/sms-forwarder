package co.nimblehq.smsforwarder.data.service.response

import com.squareup.moshi.Json

data class ForwardResponse(
    @Json(name = "message") val message: String,
    @Json(name = "success") val success: String,
    @Json(name = "data") val data: String
)
