package co.nimblehq.smsforwarder.ui.screens.filter

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.smsforwarder.domain.data.Filter
import co.nimblehq.smsforwarder.domain.data.IncomingSmsEntity
import co.nimblehq.smsforwarder.domain.usecase.ForwardIncomingSmsUseCase
import co.nimblehq.smsforwarder.domain.usecase.ObserveIncomingSmsUseCase
import co.nimblehq.smsforwarder.ui.base.BaseViewModel
import co.nimblehq.smsforwarder.ui.base.NavigationEvent
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

interface AllFiltersViewModel {
    fun navigateToFilterManager()
}

interface Input {
    fun refresh()
}

class AllFiltersViewModelImpl @ViewModelInject constructor(
    private val observeIncomingSmsUseCase: ObserveIncomingSmsUseCase,
    private val forwardIncomingSmsUseCase: ForwardIncomingSmsUseCase
) : BaseViewModel(), AllFiltersViewModel, Input {

    val input: Input = this

    private val _data = BehaviorSubject.create<List<Filter>>()
    val data: Observable<List<Filter>>
        get() = _data

    init {
        observeIncomingSms()
        _data.onNext(
            listOf(
                Filter(
                    sender = "Lydia",
                    forwardEmailAddress = "lydia@test.com",
                    forwardSlackChannel = "sms-forwarder",
                    template = ""
                ),
                Filter(
                    sender = "J.",
                    forwardEmailAddress = "",
                    forwardSlackChannel = "sms-forwarder",
                    template = ""
                )
            )
        )
    }

    // TODO Remove this
    override fun refresh() {
        observeIncomingSms()
    }

    override fun navigateToFilterManager() {
        _navigator.onNext(NavigationEvent.FilterManager)
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
