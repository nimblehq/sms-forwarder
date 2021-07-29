package co.nimblehq.smsforwarder.domain.data

import android.os.Parcelable
import co.nimblehq.smsforwarder.data.database.FilterDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val sender: String,
    val template: String,
    val forwardEmailAddress: String,
    val forwardSlackChannel: String
) : Parcelable

fun Filter.toDto() = FilterDto(
    sender = sender,
    template = template,
    forwardEmailAddress = forwardEmailAddress,
    forwardSlackChannel = forwardSlackChannel
)

fun FilterDto.toEntity() = Filter(
    sender = sender.orEmpty(),
    template = template.orEmpty(),
    forwardEmailAddress = forwardEmailAddress.orEmpty(),
    forwardSlackChannel = forwardSlackChannel.orEmpty()
)