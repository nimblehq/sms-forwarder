package co.nimblehq.smsforwarder.ui.screens.filter

import co.nimblehq.smsforwarder.domain.usecase.ForwardIncomingSmsUseCase
import co.nimblehq.smsforwarder.domain.usecase.ObserveIncomingSmsUseCase
import co.nimblehq.smsforwarder.ui.base.NavigationEvent
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Single
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test

class AllFiltersViewModelTest {

    private lateinit var viewModel: AllFiltersViewModelImpl
    private val mockObserveIncomingSmsUseCase = mock<ObserveIncomingSmsUseCase>()
    private val mockForwardIncomingSmsUseCase = mock<ForwardIncomingSmsUseCase>()

    @Before
    fun setup() {
        When calling mockForwardIncomingSmsUseCase.execute(any()) itReturns Single.just(Unit)
        viewModel =
            AllFiltersViewModelImpl(mockObserveIncomingSmsUseCase, mockForwardIncomingSmsUseCase)
    }

    @Test
    fun `When navigating to Filter screen responds positive result, it emits the corresponding navigation event`() {
        val navigatorObserver = viewModel.navigator.test()

        viewModel.navigateToFilterManager()

        navigatorObserver
            .assertValueCount(1)
            .assertValue(NavigationEvent.FilterManager)
    }

    // TODO
}
