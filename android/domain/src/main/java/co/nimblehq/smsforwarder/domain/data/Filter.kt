package co.nimblehq.smsforwarder.domain.data

import android.os.Parcelable
import co.nimblehq.smsforwarder.data.database.FilterDto
import co.nimblehq.smsforwarder.data.service.response.GetFiltersResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val id: Int = 0,
    val sender: String,
    val template: String,
    val forwardEmailAddress: String,
    val forwardSlackChannel: String
) : Parcelable

fun Filter.toDto() = FilterDto(
    id = id,
    sender = sender,
    template = template,
    forwardEmailAddress = forwardEmailAddress,
    forwardSlackChannel = forwardSlackChannel
)

fun FilterDto.toEntity() = Filter(
    id = id ?: 0,
    sender = sender.orEmpty(),
    template = template.orEmpty(),
    forwardEmailAddress = forwardEmailAddress.orEmpty(),
    forwardSlackChannel = forwardSlackChannel.orEmpty()
)

fun GetFiltersResponse.toEntities() = data.map { filter ->
    filter.toEntity()
}

fun GetFiltersResponse.FilterResponse.toEntity() = Filter(
    id = id,
    sender = sender.orEmpty(),
    template = template.orEmpty(),
    forwardEmailAddress = forwardEmailAddress.orEmpty(),
    forwardSlackChannel = forwardSlackChannel.orEmpty()
)