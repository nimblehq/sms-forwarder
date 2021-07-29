package co.nimblehq.smsforwarder.ui.screens

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import co.nimblehq.smsforwarder.R
import co.nimblehq.smsforwarder.ui.base.NavigationEvent
import co.nimblehq.smsforwarder.ui.screens.home.HomeFragmentDirections
import co.nimblehq.smsforwarder.ui.screens.login.LoginFragmentDirections
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
    fun `When navigating to Home screen from Login screen, it navigates with actionLoginFragmentToHomeFragment`() {
        When calling mockDestination.id itReturns R.id.loginFragment

        navigator.navigate(NavigationEvent.AllFilters)

        verify(mockNavController).navigate(
            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        )
    }

    @Test
    fun `When navigating to Filter screen from Home screen, it navigates with actionHomeFragmentToFilterFragment`() {
        When calling mockDestination.id itReturns R.id.allFiltersFragment

        navigator.navigate(NavigationEvent.FilterManager)

        verify(mockNavController).navigate(
            HomeFragmentDirections.actionHomeFragmentToFilterFragment()
        )
    }
}
