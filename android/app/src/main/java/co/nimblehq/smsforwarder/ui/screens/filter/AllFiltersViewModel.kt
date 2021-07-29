package co.nimblehq.smsforwarder.ui.screens.filter

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.smsforwarder.domain.data.IncomingSmsEntity
import co.nimblehq.smsforwarder.domain.usecase.ForwardIncomingSmsUseCase
import co.nimblehq.smsforwarder.domain.usecase.GetFiltersUseCase
import co.nimblehq.smsforwarder.domain.usecase.ObserveIncomingSmsUseCase
import co.nimblehq.smsforwarder.domain.usecase.ObserveFiltersUseCase
import co.nimblehq.smsforwarder.ui.base.BaseViewModel
import co.nimblehq.smsforwarder.ui.base.NavigationEvent
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

interface AllFiltersViewModel {

    fun getFilters()

    fun navigateToFilterManager()
}

interface Input {
    fun refresh()
}

class AllFiltersViewModelImpl @ViewModelInject constructor(
    private val observeIncomingSmsUseCase: ObserveIncomingSmsUseCase,
    private val forwardIncomingSmsUseCase: ForwardIncomingSmsUseCase,
    private val observeFiltersUseCase: ObserveFiltersUseCase,
    private val getFiltersUseCase: GetFiltersUseCase
) : BaseViewModel(), AllFiltersViewModel, Input {

    val input: Input = this

    private val _data = BehaviorSubject.create<List<FilterUiModel>>()
    val data: Observable<List<FilterUiModel>>
        get() = _data

    init {
        observeFilters()
        getFilters()
        observeIncomingSms()
    }

    // TODO Remove this
    override fun refresh() {
        observeIncomingSms()
    }

    override fun navigateToFilterManager() {
        _navigator.onNext(NavigationEvent.FilterManager)
    }

    override fun getFilters() {
        getFiltersUseCase
            .execute(Unit)
            .doShowLoading()
            .subscribe()
            .addToDisposables()
    }

    private fun observeIncomingSms() {
        observeIncomingSmsUseCase
            .execute(Unit)
            .subscribe(::forwardIncomingSmsIfMatchedFilter)
            .addToDisposables()
    }

    private fun observeFilters() {
        observeFiltersUseCase
            .execute(Unit)
            .map { filters ->
                filters.map { item -> item.toUiModel() }
            }
            .subscribe(_data::onNext)
            .addToDisposables()
    }

    private fun forwardIncomingSmsIfMatchedFilter(entity: IncomingSmsEntity) {
        _data.value.orEmpty()
            .firstOrNull {
                entity.incomingNumber == it.template
            }?.let {
                val input = ForwardIncomingSmsUseCase.Input(
                    entity.incomingNumber,
                    entity.messageBody,
                    it.forwardEmailAddress
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
}
