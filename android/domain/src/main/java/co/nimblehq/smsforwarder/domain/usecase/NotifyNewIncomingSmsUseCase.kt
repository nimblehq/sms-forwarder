package co.nimblehq.smsforwarder.domain.usecase

import co.nimblehq.smsforwarder.domain.data.IncomingSmsEntity
import co.nimblehq.smsforwarder.domain.data.error.DataError.GetDataError
import co.nimblehq.smsforwarder.domain.persistence.IncomingSmsPersistence
import co.nimblehq.smsforwarder.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.smsforwarder.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class NotifyNewIncomingSmsUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val persistence: IncomingSmsPersistence
) : SingleUseCase<NotifyNewIncomingSmsUseCase.Input, Unit>(
    schedulerProvider.io(),
    schedulerProvider.main(),
    ::GetDataError
) {

    data class Input(
        val sender: String,
        val messageBody: String
    )

    override fun create(input: Input): Single<Unit> {
        val entity = IncomingSmsEntity(
            input.sender,
            input.messageBody
        )
        return Single.fromCallable {
            persistence.newIncomingSms(entity)
        }
    }
}
