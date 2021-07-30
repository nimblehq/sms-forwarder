package co.nimblehq.smsforwarder.ui.screens.filter

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.smsforwarder.domain.data.Filter
import co.nimblehq.smsforwarder.domain.data.IncomingSmsEntity
import co.nimblehq.smsforwarder.domain.usecase.*
import co.nimblehq.smsforwarder.ui.base.BaseViewModel
import co.nimblehq.smsforwarder.ui.base.NavigationEvent
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

interface Input {
    fun refresh()
}

class AllFiltersViewModel @ViewModelInject constructor(
    private val observeIncomingSmsUseCase: ObserveIncomingSmsUseCase,
    private val forwardIncomingSmsUseCase: ForwardIncomingSmsUseCase,
    private val observeFiltersUseCase: ObserveFiltersUseCase,
    private val getLocalFiltersUseCase: GetLocalFiltersUseCase,
    private val getRemoteFiltersUseCase: GetRemoteFiltersUseCase,
    private val updateLocalFiltersUseCase: UpdateLocalFiltersUseCase
) : BaseViewModel(), Input {

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

    fun navigateToFilterManager() {
        _navigator.onNext(NavigationEvent.FilterManager)
    }

    fun getRemoteFilters() {
        getRemoteFiltersUseCase
            .execute(
                GetRemoteFiltersUseCase.Input()
            )
            .doShowLoading()
            .subscribeBy(
                onSuccess = ::updateLocalFilters,
                onError = _error::onNext
            )
            .addToDisposables()
    }

    private fun updateLocalFilters(filters: List<Filter>) {
        updateLocalFiltersUseCase
            .execute(
                UpdateLocalFiltersUseCase.Input(filters)
            )
            .doShowLoading()
            .subscribe()
            .addToDisposables()
    }

    private fun getFilters() {
        getLocalFiltersUseCase
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
                entity.incomingNumber.equals(it.sender, ignoreCase = true)
            }?.let {
                val input = ForwardIncomingSmsUseCase.Input(
                    entity.incomingNumber,
                    entity.messageBody,
                    it.forwardEmailAddress,
                    it.forwardSlackChannel
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
