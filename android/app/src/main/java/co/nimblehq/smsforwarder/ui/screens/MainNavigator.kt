package co.nimblehq.smsforwarder.ui.screens

import androidx.fragment.app.Fragment
import co.nimblehq.smsforwarder.R
import co.nimblehq.smsforwarder.ui.base.*
import co.nimblehq.smsforwarder.ui.screens.filter.AllFiltersFragmentDirections
import co.nimblehq.smsforwarder.ui.screens.login.LoginFragmentDirections
import javax.inject.Inject

interface MainNavigator : BaseNavigator

class MainNavigatorImpl @Inject constructor(
    fragment: Fragment
) : BaseNavigatorImpl(fragment), MainNavigator {

    override val navHostFragmentId = R.id.fcvMain

    override fun navigate(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.AllFilters -> navigateToHome()
            is NavigationEvent.FilterManager -> navigateToFilter()
        }
    }

    private fun navigateToHome() {
        findNavController()?.navigate(
            LoginFragmentDirections.actionLoginFragmentToAllFiltersFragment()
        )
    }

    private fun navigateToFilter() {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.allFiltersFragment -> navController.navigate(
                AllFiltersFragmentDirections.actionAllFiltersFragmentToFilterManagerFragment()
            )
            else -> unsupportedNavigation()
        }
    }
}
