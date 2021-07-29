package co.nimblehq.smsforwarder.domain.usecase

import co.nimblehq.smsforwarder.domain.data.Filter
import co.nimblehq.smsforwarder.domain.data.error.DataError.GetDataError
import co.nimblehq.smsforwarder.domain.persistence.FiltersPersistence
import co.nimblehq.smsforwarder.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.smsforwarder.domain.usecase.base.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class SaveFiltersUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val persistence: FiltersPersistence
) : CompletableUseCase<Filter>(
    schedulerProvider.io(),
    schedulerProvider.main(),
    ::GetDataError
) {

    override fun create(input: Filter): Completable {
        return persistence
            .insert(input)
    }
}
