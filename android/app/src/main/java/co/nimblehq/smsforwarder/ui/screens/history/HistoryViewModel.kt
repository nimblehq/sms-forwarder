package co.nimblehq.smsforwarder.ui.screens.history

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.smsforwarder.ui.base.BaseViewModel
import co.nimblehq.smsforwarder.ui.screens.filter.manager.Input

interface Input {
    // TODO: Implement in integrate task
}

class HistoryViewModel @ViewModelInject constructor() : BaseViewModel(), Input {

    val input: Input = this

}
