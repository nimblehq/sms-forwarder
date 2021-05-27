package co.nimblehq.smsforwarder.ui.screens

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import co.nimblehq.smsforwarder.R
import co.nimblehq.smsforwarder.domain.test.MockUtil
import co.nimblehq.smsforwarder.ui.base.NavigationEvent
import co.nimblehq.smsforwarder.ui.screens.home.HomeFragmentDirections
import co.nimblehq.smsforwarder.ui.screens.second.SecondBundle
import co.nimblehq.smsforwarder.ui.screens.second.SecondFragmentDirections
import co.nimblehq.smsforwarder.ui.screens.webview.WebViewBundle
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test
import org.robolectric.util.ReflectionHelpers

class MainNavigatorTest {

    private lateinit var navigator: MainNavigator
    private val mockFragment = mock<Fragment>()
    private val mockNavController = mock<NavController>()
    private val mockDestination = mock<NavDestination>()

    @Before
    fun setup() {
        navigator = MainNavigatorImpl(mockFragment)
        ReflectionHelpers.setField(navigator, "navController", mockNavController)

        When calling mockNavController.currentDestination itReturns mockDestination
    }

    @Test
    fun `When navigating to Second screen from Home screen, it navigates with corresponding bundle`() {
        When calling mockDestination.id itReturns R.id.homeFragment

        val bundle = SecondBundle(MockUtil.dataList[0])
        navigator.navigate(
            NavigationEvent.Second(bundle)
        )

        verify(mockNavController).navigate(
            HomeFragmentDirections.actionHomeFragmentToSecondFragment(bundle)
        )
    }

    @Test
    fun `When navigating to WebView screen from Second screen, it navigates with corresponding bundle`() {
        When calling mockDestination.id itReturns R.id.filterFragment

        val bundle = WebViewBundle("url")
        navigator.navigate(
            NavigationEvent.WebView(bundle)
        )

        verify(mockNavController).navigate(
            SecondFragmentDirections.actionSecondFragmentToWebViewFragment(bundle)
        )
    }
}
