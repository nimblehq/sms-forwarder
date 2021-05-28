package co.nimblehq.smsforwarder.domain.data

data class IncomingSmsEntity(
    val incomingNumber: String,
    val messageBody: String,
    val emailAddress: String
)
