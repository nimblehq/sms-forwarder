package co.nimblehq.smsforwarder.ui.base

import co.nimblehq.smsforwarder.ui.screens.second.SecondBundle
import co.nimblehq.smsforwarder.ui.screens.webview.WebViewBundle

sealed class NavigationEvent {
    object Home : NavigationEvent()
    data class Second(val bundle: SecondBundle) : NavigationEvent()
    data class WebView(val bundle: WebViewBundle) : NavigationEvent()
}
