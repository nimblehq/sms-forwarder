package co.nimblehq.smsforwarder.domain.repository

import co.nimblehq.smsforwarder.data.service.ApiService
import co.nimblehq.smsforwarder.data.service.request.ForwardRequest
import co.nimblehq.smsforwarder.domain.transform
import io.reactivex.Single
import javax.inject.Inject

interface ApiRepository {

    fun forward(
        incomingNumber: String,
        messageBody: String,
        email: String
    ): Single<Unit>
}

class ApiRepositoryImpl @Inject constructor(
    private val service: ApiService
) : ApiRepository {

    override fun forward(
        incomingNumber: String,
        messageBody: String,
        email: String
    ): Single<Unit> {
        val request = ForwardRequest(
            incomingNumber = incomingNumber,
            messageBody = messageBody,
            email = email
        )
        return service
            .forward(request)
            .transform()
            .map {}
    }
}
