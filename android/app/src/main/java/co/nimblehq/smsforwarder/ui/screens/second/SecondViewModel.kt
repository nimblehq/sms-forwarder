package co.nimblehq.smsforwarder.ui.screens.second

import androidx.hilt.lifecycle.ViewModelInject
import co.nimblehq.smsforwarder.domain.data.Data
import co.nimblehq.smsforwarder.ui.base.BaseViewModel
import co.nimblehq.smsforwarder.ui.base.NavigationEvent
import co.nimblehq.smsforwarder.ui.screens.webview.WebViewBundle
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface Input {
    fun dataFromIntent(data: Data)

    fun openPost()
}

class SecondViewModel @ViewModelInject constructor() : BaseViewModel(), Input {

    val input: Input = this

    private val _data = BehaviorSubject.create<Data>()
    val data: Observable<Data>
        get() = _data

    override fun dataFromIntent(data: Data) {
        _data.onNext(data)
    }

    override fun openPost() {
        _navigator.onNext(
            NavigationEvent.WebView(
                WebViewBundle(_data.value?.url.orEmpty())
            )
        )
    }
}
