package co.nimblehq.smsforwarder.ui.screens.webview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import co.nimblehq.smsforwarder.databinding.FragmentWebviewBinding
import co.nimblehq.smsforwarder.databinding.ViewLoadingBinding
import co.nimblehq.smsforwarder.extension.initialSetup
import co.nimblehq.smsforwarder.extension.visibleOrGone
import co.nimblehq.smsforwarder.lib.IsLoading
import co.nimblehq.smsforwarder.ui.base.BaseFragment
import co.nimblehq.smsforwarder.ui.helpers.handleVisualOverlaps
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment<FragmentWebviewBinding>() {

    private val viewModel by viewModels<WebViewViewModel>()
    private val args: WebViewFragmentArgs by navArgs()
    private val bundle: WebViewBundle by lazy { args.bundle }

    private lateinit var viewLoadingBinding: ViewLoadingBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWebviewBinding
        get() = { inflater, container, attachToParent ->
            FragmentWebviewBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)
    }

    override fun handleVisualOverlaps() {
        binding.webView.handleVisualOverlaps()
    }

    override fun bindViewModel() {
        viewModel.showLoading bindTo ::bindLoading
        viewModel.error bindTo ::displayError
        viewModel.startUrl bindTo ::loadUrl

        viewModel.input.loadUrl(bundle.url)
    }

    private fun bindLoading(isLoading: IsLoading) {
        viewLoadingBinding.pbLoading.visibleOrGone(isLoading)
    }

    private fun loadUrl(startUrl: String) {
        val webChromeClient = AppWebChromeClient {
            viewModel.progress(it)
        }
        with(binding.webView) {
            initialSetup(WebViewClient(), webChromeClient)
            loadUrl(startUrl)
        }
    }
}
