package co.nimblehq.smsforwarder.domain.data.error

sealed class ValidationError(
    override val cause: Throwable?
) : AppError(cause) {

    data class EmailValidationError(override val cause: Throwable?) : ValidationError(cause)

    data class PasswordValidationError(override val cause: Throwable?) : ValidationError(cause)
}
