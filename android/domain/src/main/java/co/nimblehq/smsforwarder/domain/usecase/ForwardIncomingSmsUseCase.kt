package co.nimblehq.smsforwarder.domain.usecase

import co.nimblehq.smsforwarder.domain.data.error.DataError.GetDataError
import co.nimblehq.smsforwarder.domain.repository.ApiRepository
import co.nimblehq.smsforwarder.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.smsforwarder.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class ForwardIncomingSmsUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val repository: ApiRepository
) : SingleUseCase<ForwardIncomingSmsUseCase.Input, Unit>(
    schedulerProvider.io(),
    schedulerProvider.main(),
    ::GetDataError
) {

    data class Input(
        val incomingNumber: String,
        val messageBody: String,
        val email: String
    )

    override fun create(input: Input): Single<Unit> {
        return with(input) {
            repository.forward(
                incomingNumber = incomingNumber,
                messageBody = messageBody,
                email = email
            )
        }
    }
}
