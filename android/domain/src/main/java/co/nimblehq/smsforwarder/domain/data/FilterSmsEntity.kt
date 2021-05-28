package co.nimblehq.smsforwarder.domain.data

data class FilterSmsEntity(
    val incomingNumber: String,
    val forwardEmailAddress: String
)
