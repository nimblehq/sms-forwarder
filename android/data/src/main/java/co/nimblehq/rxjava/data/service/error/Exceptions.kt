package co.nimblehq.smsforwarder.data.service.error

import co.nimblehq.smsforwarder.data.service.response.ErrorResponse
import retrofit2.HttpException
import retrofit2.Response

object UnknownException : RuntimeException()

object NoConnectivityException : RuntimeException()

data class JsonApiException(val error: ErrorResponse, val response: Response<*>) :
    HttpException(response)
