package co.nimblehq.smsforwarder.domain.usecase

import co.nimblehq.smsforwarder.domain.data.Filter
import co.nimblehq.smsforwarder.domain.data.error.DataError.GetDataError
import co.nimblehq.smsforwarder.domain.repository.ApiRepository
import co.nimblehq.smsforwarder.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.smsforwarder.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetRemoteFiltersUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val repository: ApiRepository
) : SingleUseCase<GetRemoteFiltersUseCase.Input, List<Filter>>(
    schedulerProvider.io(),
    schedulerProvider.main(),
    ::GetDataError
) {

    data class Input(
        val limit: Int = 10,
        val offset: Int = 0
    )

    override fun create(input: Input): Single<List<Filter>> {
        return repository.getFilters(input.limit, input.offset)
    }
}
