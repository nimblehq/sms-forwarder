package co.nimblehq.smsforwarder.ui

import android.content.Context
import co.nimblehq.smsforwarder.R
import co.nimblehq.smsforwarder.domain.data.error.AppError
import co.nimblehq.smsforwarder.domain.data.error.ValidateError

fun Throwable.userReadableMessage(context: Context, vararg formatArgs: Any?): String {
    // FIXME Check AppError instead. Other specific Error should be added into UseCase
    val customErrorMessage = when (this) {
        is ValidateError.InvalidEmailError -> context.getString(R.string.error_email_invalid)
        is ValidateError.InvalidPasswordError -> context.getString(
            R.string.error_password_invalid,
            formatArgs[0].toString()
        )
        else -> null
    }
    return customErrorMessage
        ?: (this as? AppError)?.readableMessage
        ?: context.getString(R.string.error_generic)
}
