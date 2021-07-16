package co.nimblehq.smsforwarder.data.service.request

import com.squareup.moshi.Json

data class ForwardRequest(
    @Json(name = "incoming_number") val incomingNumber: String,
    @Json(name = "message_body") val messageBody: String,
    @Json(name = "email") val email: String
)
