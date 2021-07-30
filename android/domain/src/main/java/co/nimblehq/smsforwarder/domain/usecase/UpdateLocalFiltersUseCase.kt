package co.nimblehq.smsforwarder.domain.usecase

import co.nimblehq.smsforwarder.domain.data.Filter
import co.nimblehq.smsforwarder.domain.data.error.DataError.GetDataError
import co.nimblehq.smsforwarder.domain.persistence.FiltersPersistence
import co.nimblehq.smsforwarder.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.smsforwarder.domain.usecase.base.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class UpdateLocalFiltersUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val persistence: FiltersPersistence
) : CompletableUseCase<UpdateLocalFiltersUseCase.Input>(
    schedulerProvider.io(),
    schedulerProvider.main(),
    ::GetDataError
) {

    data class Input(
        val filters: List<Filter>
    )

    override fun create(input: Input): Completable {
        return persistence.replaceAll(input.filters)
    }
}
