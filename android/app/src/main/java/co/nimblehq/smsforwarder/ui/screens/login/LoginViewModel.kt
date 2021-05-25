package co.nimblehq.smsforwarder.ui.screens.login

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.smsforwarder.ui.base.BaseViewModel
import co.nimblehq.smsforwarder.ui.base.NavigationEvent

interface Input {
    fun navigateToFilter()
}

class LoginViewModel @ViewModelInject constructor() : BaseViewModel(), Input {

    val input: Input = this

    override fun navigateToFilter() {
        _navigator.onNext(NavigationEvent.Home)
    }
}
