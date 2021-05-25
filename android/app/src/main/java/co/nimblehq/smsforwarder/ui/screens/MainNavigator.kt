package co.nimblehq.smsforwarder.ui.screens

import androidx.fragment.app.Fragment
import co.nimblehq.smsforwarder.R
import co.nimblehq.smsforwarder.ui.base.*
import co.nimblehq.smsforwarder.ui.screens.home.HomeFragmentDirections
import co.nimblehq.smsforwarder.ui.screens.login.LoginFragmentDirections
import co.nimblehq.smsforwarder.ui.screens.second.SecondBundle
import co.nimblehq.smsforwarder.ui.screens.second.SecondFragmentDirections
import co.nimblehq.smsforwarder.ui.screens.webview.WebViewBundle
import javax.inject.Inject

interface MainNavigator : BaseNavigator

class MainNavigatorImpl @Inject constructor(
    fragment: Fragment
) : BaseNavigatorImpl(fragment), MainNavigator {

    override val navHostFragmentId = R.id.navHostFragment

    override fun navigate(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.Home -> navigateToHome()
            is NavigationEvent.Second -> navigateToSecond(event.bundle)
            is NavigationEvent.WebView -> navigateToWebView(event.bundle)
        }
    }

    private fun navigateToHome() {
        findNavController()?.navigate(
            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        )
    }

    private fun navigateToSecond(bundle: SecondBundle) {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.homeFragment -> navController.navigate(
                HomeFragmentDirections.actionHomeFragmentToSecondFragment(bundle)
            )
            else -> unsupportedNavigation()
        }
    }

    private fun navigateToWebView(bundle: WebViewBundle) {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.secondFragment -> navController.navigate(
                SecondFragmentDirections.actionSecondFragmentToWebViewFragment(bundle)
            )
            else -> unsupportedNavigation()
        }
    }
}
