package co.nimblehq.smsforwarder.domain.usecase

import co.nimblehq.smsforwarder.domain.data.Filter
import co.nimblehq.smsforwarder.domain.data.error.DataError.GetDataError
import co.nimblehq.smsforwarder.domain.persistence.FiltersPersistence
import co.nimblehq.smsforwarder.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.smsforwarder.domain.usecase.base.FlowableUseCase
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject

class ObserveFiltersUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val persistence: FiltersPersistence
) : FlowableUseCase<Unit, List<Filter>>(
    schedulerProvider.io(),
    schedulerProvider.main(),
    ::GetDataError
) {

    override fun create(input: Unit): Flowable<List<Filter>> {
        return persistence
            .filters
            .toFlowable(BackpressureStrategy.LATEST)
    }
}
