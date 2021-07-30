package co.nimblehq.smsforwarder.ui.screens.filter

import co.nimblehq.smsforwarder.domain.data.Filter

data class FilterUiModel(
    val sender: String,
    val template: String,
    val forwardEmailAddress: String,
    val forwardSlackChannel: String
)

fun Filter.toUiModel() = FilterUiModel(
    sender = sender,
    template = template,
    forwardEmailAddress = forwardEmailAddress,
    forwardSlackChannel = forwardSlackChannel
)
