package co.nimblehq.smsforwarder.ui.screens.home

import co.nimblehq.smsforwarder.domain.data.error.DataError
import co.nimblehq.smsforwarder.domain.test.MockUtil
import co.nimblehq.smsforwarder.domain.usecase.GetExampleDataUseCase
import co.nimblehq.smsforwarder.ui.base.NavigationEvent
import co.nimblehq.smsforwarder.ui.screens.second.SecondBundle
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Single
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var viewModelImpl: HomeViewModelImpl
    private val mockGetExampleDataUseCase = mock<GetExampleDataUseCase>()

    @Before
    fun setup() {
        When calling mockGetExampleDataUseCase.execute(any()) itReturns Single.just(MockUtil.dataList)
        viewModelImpl = HomeViewModelImpl(mockGetExampleDataUseCase)
    }

    @Test
    fun `When initializing the view model, it emits the first data correspondingly`() {
        val dataObserver = viewModelImpl.data.test()

        dataObserver
            .assertValueCount(1)
            .assertValue(MockUtil.dataList)
    }

    @Test
    fun `When initializing the view model, it doesn't emit to show any loading indicator, defaulting to false`() {
        val loadingObserver = viewModelImpl.showLoading.test()

        loadingObserver
            .assertNoErrors()
            .assertValue(false)
    }

    @Test
    fun `When calling refresh responds positive result, it emits the second data correspondingly`() {
        val dataObserver = viewModelImpl.data.test()

        viewModelImpl.input.refresh()

        dataObserver
            .assertValueCount(2)
            .assertValues(MockUtil.dataList, MockUtil.dataList)
    }

    @Test
    fun `When calling refresh regardless of success or failure, it emits to 2 new states to showLoading as true then false`() {
        val loadingObserver = viewModelImpl.showLoading.test()

        viewModelImpl.input.refresh()

        loadingObserver
            .assertNoErrors()
            .assertValues(false, true, false)
    }

    @Test
    fun `When calling refresh responds any negative result, it emits the corresponding error`() {
        When calling mockGetExampleDataUseCase.execute(any()) itReturns
            Single.error(DataError.GetDataError(null))
        val testObserver = viewModelImpl.error.test()

        viewModelImpl.input.refresh()

        testObserver
            .assertNoErrors()
            .assertValue { it is DataError.GetDataError }
    }

    @Test
    fun `When navigating to Detail screen responds positive result, it emits the corresponding navigation event`() {
        val navigatorObserver = viewModelImpl.navigator.test()

        viewModelImpl.navigateToDetail(MockUtil.dataList[0])

        navigatorObserver
            .assertValueCount(1)
            .assertValue(
                NavigationEvent.Second(
                    SecondBundle(MockUtil.dataList[0])
                )
            )
    }
}
