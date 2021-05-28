package co.nimblehq.smsforwarder.domain.repository

import co.nimblehq.smsforwarder.data.service.ApiService
import co.nimblehq.smsforwarder.domain.data.Sms
import co.nimblehq.smsforwarder.domain.data.toDataList
import co.nimblehq.smsforwarder.domain.transform
import io.reactivex.Single
import javax.inject.Inject

interface ApiRepository {

    fun exampleData(): Single<List<Sms>>
}

class ApiRepositoryImpl @Inject constructor(
    private val service: ApiService
) : ApiRepository {

    override fun exampleData(): Single<List<Sms>> {
        return service
            .getExampleData()
            .transform()
            .map { it.toDataList() }
    }
}
