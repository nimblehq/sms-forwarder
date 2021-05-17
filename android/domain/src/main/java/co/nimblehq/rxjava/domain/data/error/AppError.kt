package co.nimblehq.smsforwarder.domain.data.error

import co.nimblehq.smsforwarder.data.service.error.JsonApiException

open class AppError(
    override val cause: Throwable?
) : Throwable(cause) {

    val readableMessage: String?
        get() = (cause as? JsonApiException)?.error?.message
}

class Ignored(cause: Throwable?) : AppError(cause)

class NoConnectivityError(cause: Throwable?) : AppError(cause)
