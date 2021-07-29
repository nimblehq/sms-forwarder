package co.nimblehq.smsforwarder.ui.screens.filter.manager

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.smsforwarder.domain.data.Filter
import co.nimblehq.smsforwarder.domain.usecase.SaveFiltersUseCase
import co.nimblehq.smsforwarder.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface Input {
    // TODO: Implement in integrate task
}

class FilterManagerViewModel @ViewModelInject constructor(
    private val saveFiltersUseCase: SaveFiltersUseCase
) : BaseViewModel(), Input {

    val input: Input = this

    private val _saveFilterSuccess = BehaviorSubject.create<Unit>()
    val saveFilterSuccess: Observable<Unit>
        get() = _saveFilterSuccess

    fun saveFilter(
        sender: String,
        smsTemplate: String,
        forwardEmail: String,
        forwardSlackChannel: String
    ): Completable {
        val input = Filter(
            sender = sender,
            template = smsTemplate,
            forwardEmailAddress = forwardEmail,
            forwardSlackChannel = forwardSlackChannel
        )
        return saveFiltersUseCase
            .execute(input)
            .doShowLoading()
    }

}
