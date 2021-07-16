package co.nimblehq.smsforwarder.ui.base

sealed class NavigationEvent {
    object Home : NavigationEvent()
    object Filter : NavigationEvent()
}
