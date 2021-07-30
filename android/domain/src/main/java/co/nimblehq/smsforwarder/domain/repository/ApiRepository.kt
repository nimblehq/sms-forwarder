package co.nimblehq.smsforwarder.domain.repository

import co.nimblehq.smsforwarder.data.service.ApiService
import co.nimblehq.smsforwarder.data.service.request.ForwardRequest
import co.nimblehq.smsforwarder.domain.data.Filter
import co.nimblehq.smsforwarder.domain.data.toEntities
import co.nimblehq.smsforwarder.domain.transform
import io.reactivex.Single
import javax.inject.Inject

interface ApiRepository {

    fun forward(
        incomingNumber: String,
        messageBody: String,
        email: String,
        slackWebhook: String
    ): Single<Unit>

    fun getFilters(
        limit: Int,
        offset: Int
    ): Single<List<Filter>>
}

class ApiRepositoryImpl @Inject constructor(
    private val service: ApiService
) : ApiRepository {

    override fun forward(
        incomingNumber: String,
        messageBody: String,
        email: String,
        slackWebhook: String
    ): Single<Unit> {
        val request = ForwardRequest(
            incomingNumber = incomingNumber,
            messageBody = messageBody,
            email = email,
            slackWebhook = slackWebhook
        )
        return service
            .forward(request)
            .transform()
            .map {}
    }

    override fun getFilters(
        limit: Int,
        offset: Int
    ): Single<List<Filter>> {
        return service.getFilters(limit, offset)
            .transform()
            .map { it.toEntities() }
    }
}
