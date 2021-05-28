package co.nimblehq.smsforwarder.domain.usecase

import co.nimblehq.smsforwarder.domain.data.IncomingSmsEntity
import co.nimblehq.smsforwarder.domain.data.error.DataError.GetDataError
import co.nimblehq.smsforwarder.domain.persistence.IncomingSmsPersistence
import co.nimblehq.smsforwarder.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.smsforwarder.domain.usecase.base.FlowableUseCase
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject

class ObserveIncomingSmsUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val persistence: IncomingSmsPersistence
) : FlowableUseCase<Unit, IncomingSmsEntity>(
    schedulerProvider.io(),
    schedulerProvider.main(),
    ::GetDataError
) {

    override fun create(input: Unit): Flowable<IncomingSmsEntity> {
        return persistence
            .incomingSms
            .toFlowable(BackpressureStrategy.LATEST)
    }
}
