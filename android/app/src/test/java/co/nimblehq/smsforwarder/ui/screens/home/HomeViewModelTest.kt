package co.nimblehq.smsforwarder.ui.screens.home

import co.nimblehq.smsforwarder.domain.test.MockUtil
import co.nimblehq.smsforwarder.domain.usecase.ForwardIncomingSmsUseCase
import co.nimblehq.smsforwarder.ui.base.NavigationEvent
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Single
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModelImpl
    private val mockForwardIncomingSmsUseCase = mock<ForwardIncomingSmsUseCase>()

    @Before
    fun setup() {
        When calling mockForwardIncomingSmsUseCase.execute(any()) itReturns Single.just(MockUtil.dataList)
        viewModel = HomeViewModelImpl(mockForwardIncomingSmsUseCase)
    }

    @Test
    fun `When navigating to Filter screen responds positive result, it emits the corresponding navigation event`() {
        val navigatorObserver = viewModel.navigator.test()

        viewModel.navigateToFilter()

        navigatorObserver
            .assertValueCount(1)
            .assertValue(NavigationEvent.Filter)
    }

    // TODO
}
