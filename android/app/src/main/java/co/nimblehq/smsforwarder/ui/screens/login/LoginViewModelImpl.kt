package co.nimblehq.smsforwarder.ui.screens.login

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.smsforwarder.ui.base.BaseViewModel
import co.nimblehq.smsforwarder.ui.base.NavigationEvent

interface LoginViewModel {
    fun navigateToFilter()
}

class LoginViewModelImpl @ViewModelInject constructor() : BaseViewModel(), LoginViewModel {

    override fun navigateToFilter() {
        _navigator.onNext(NavigationEvent.Home)
    }
}
