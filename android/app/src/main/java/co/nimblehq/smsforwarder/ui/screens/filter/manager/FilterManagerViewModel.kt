package co.nimblehq.smsforwarder.ui.screens.filter.manager

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.smsforwarder.ui.base.BaseViewModel

interface Input {
    // TODO: Implement in integrate task
}

class FilterManagerViewModel @ViewModelInject constructor() : BaseViewModel(), Input {

    val input: Input = this

}
