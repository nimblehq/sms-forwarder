package co.nimblehq.smsforwarder.ui.base

sealed class NavigationEvent {
    object AllFilters : NavigationEvent()
    object FilterManager : NavigationEvent()
}
