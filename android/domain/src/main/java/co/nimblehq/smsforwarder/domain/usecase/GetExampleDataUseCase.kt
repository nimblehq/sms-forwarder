package co.nimblehq.smsforwarder.domain.usecase

import co.nimblehq.smsforwarder.domain.data.Sms
import co.nimblehq.smsforwarder.domain.data.error.DataError.GetDataError
import co.nimblehq.smsforwarder.domain.repository.ApiRepository
import co.nimblehq.smsforwarder.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.smsforwarder.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetExampleDataUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val repository: ApiRepository
) : SingleUseCase<Unit, List<Sms>>(
    schedulerProvider.io(),
    schedulerProvider.main(),
    ::GetDataError
) {

    override fun create(input: Unit): Single<List<Sms>> {
        return repository.exampleData()
    }
}
