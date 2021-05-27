package co.nimblehq.smsforwarder.ui.screens.second

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.smsforwarder.ui.base.BaseViewModel

interface Input {
    // TODO: Implement in integrate task
}

class FilterViewModel @ViewModelInject constructor() : BaseViewModel(), Input {

    val input: Input = this

}
