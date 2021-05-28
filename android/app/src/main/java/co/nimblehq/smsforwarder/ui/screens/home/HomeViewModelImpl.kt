package co.nimblehq.smsforwarder.ui.screens.home

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.smsforwarder.domain.data.IncomingSmsEntity
import co.nimblehq.smsforwarder.domain.data.Sms
import co.nimblehq.smsforwarder.domain.usecase.ForwardIncomingSmsUseCase
import co.nimblehq.smsforwarder.domain.usecase.ObserveIncomingSmsUseCase
import co.nimblehq.smsforwarder.ui.base.BaseViewModel
import co.nimblehq.smsforwarder.ui.base.NavigationEvent
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

interface HomeViewModel {
    fun navigateToFilter()
}

interface Input {
    fun refresh()
}

class HomeViewModelImpl @ViewModelInject constructor(
    private val observeIncomingSmsUseCase: ObserveIncomingSmsUseCase,
    private val forwardIncomingSmsUseCase: ForwardIncomingSmsUseCase
) : BaseViewModel(), HomeViewModel, Input {

    val input: Input = this

    private val _data = BehaviorSubject.create<List<Sms>>()
    val data: Observable<List<Sms>>
        get() = _data

    init {
        observeIncomingSms()
    }

    // TODO Remove this
    override fun refresh() {
        observeIncomingSms()
    }

    override fun navigateToFilter() {
        _navigator.onNext(NavigationEvent.Filter)
    }

    private fun observeIncomingSms() {
        observeIncomingSmsUseCase
            .execute(Unit)
            .subscribe {
                forwardIncomingSms(it)
            }
            .addToDisposables()
    }

    private fun forwardIncomingSms(entity: IncomingSmsEntity) {
        val input = ForwardIncomingSmsUseCase.Input(
            entity.incomingNumber,
            entity.messageBody,
            "hoang.l@nimblehq.co" // FIXME
        )
        forwardIncomingSmsUseCase
            .execute(input)
            .doShowLoading()
            .subscribeBy(
                onSuccess = {},
                onError = _error::onNext
            )
            .addToDisposables()
    }
}
