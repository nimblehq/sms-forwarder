package co.nimblehq.smsforwarder.domain

import co.nimblehq.smsforwarder.data.service.error.JsonApiException
import co.nimblehq.smsforwarder.data.service.error.NoConnectivityException
import co.nimblehq.smsforwarder.data.service.error.UnknownException
import co.nimblehq.smsforwarder.data.service.providers.MoshiBuilderProvider
import co.nimblehq.smsforwarder.data.service.response.ErrorResponse
import io.reactivex.Single
import retrofit2.Response
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Single<Response<T>>.transform(): Single<T> = flatMap {
    val body = it.body()
    if (it.isSuccessful && body != null) {
        Single.just(body)
    } else {
        Single.error(mapError(it))
    }
}.onErrorResumeNext {
    when (it) {
        is SocketTimeoutException -> Single.error(it)
        is UnknownHostException,
        is InterruptedIOException -> Single.error(NoConnectivityException)
        else -> Single.error(it)
    }
}

private fun <T> mapError(response: Response<T>?): Exception {
    val errorBody = response?.errorBody()?.string()
    val errorResponse = parseErrorResponse(errorBody)

    return if (errorResponse != null && response != null) JsonApiException(errorResponse, response)
    else UnknownException
}

private fun parseErrorResponse(source: String?): ErrorResponse? {
    return try {
        val moshi = MoshiBuilderProvider.moshiBuilder.build()
        val adapter = moshi.adapter(ErrorResponse::class.java)
        adapter.fromJson(source.orEmpty())
    } catch (e: Exception) {
        null
    }
}
