package co.nimblehq.smsforwarder.data.service.response

import com.squareup.moshi.Json

data class GetFiltersResponse(
    @Json(name = "message") val message: String,
    @Json(name = "success") val success: String,
    @Json(name = "data") val data: List<FilterResponse>
) {

    data class FilterResponse(
        @Json(name = "id") val id: Int,
        @Json(name = "sender") val sender: String?,
        @Json(name = "template") val template: String?,
        @Json(name = "forwardEmailAddress") val forwardEmailAddress: String?,
        @Json(name = "forwardSlackChannel") val forwardSlackChannel: String?,
    )
}
