package co.nimblehq.smsforwarder.ui.screens.filter.manager

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.smsforwarder.domain.data.Filter
import co.nimblehq.smsforwarder.domain.usecase.SaveFiltersUseCase
import co.nimblehq.smsforwarder.ui.base.BaseViewModel

interface FilterManagerViewModel {

    fun saveFilter(
        sender: String,
        smsTemplate: String,
        forwardEmail: String,
        forwardSlackChannel: String
    )
}

interface Input {
    // TODO: Implement in integrate task
}

class FilterManagerViewModelImpl @ViewModelInject constructor(
    private val saveFiltersUseCase: SaveFiltersUseCase
) : BaseViewModel(), FilterManagerViewModel, Input {

    val input: Input = this

    override fun saveFilter(
        sender: String,
        smsTemplate: String,
        forwardEmail: String,
        forwardSlackChannel: String
    ) {
        val input = Filter(
            sender = sender,
            template = smsTemplate,
            forwardEmailAddress = forwardEmail,
            forwardSlackChannel = forwardSlackChannel
        )
        saveFiltersUseCase
            .execute(input)
            .doShowLoading()
            .subscribe()
            .addToDisposables()
    }

}
