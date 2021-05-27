package co.nimblehq.smsforwarder.ui.screens.second

import co.nimblehq.smsforwarder.domain.test.MockUtil
import co.nimblehq.smsforwarder.ui.base.NavigationEvent
import co.nimblehq.smsforwarder.ui.screens.webview.WebViewBundle
import org.junit.Before
import org.junit.Test

class FilterViewModelTest {

    private lateinit var viewModel: FilterViewModel

    @Before
    fun setup() {
        viewModel = FilterViewModel()
    }

    @Test
    fun `When calling dataFromIntent responds positive result, it emits success data correspondingly`() {
        val dataObserver = viewModel.data.test()

        viewModel.dataFromIntent(MockUtil.dataList[0])

        dataObserver
            .assertValueCount(1)
            .assertValue(MockUtil.dataList[0])
    }

    @Test
    fun `When opening a post, it navigates to WebView screen correctly`() {
        val navigatorObserver = viewModel.navigator.test()

        viewModel.dataFromIntent(MockUtil.dataList[0])
        viewModel.openPost()

        navigatorObserver
            .assertValueCount(1)
            .assertValue(
                NavigationEvent.WebView(WebViewBundle("url"))
            )
    }
}
