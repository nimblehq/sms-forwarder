package co.nimblehq.smsforwarder.ui.screens.filter.manager

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import co.nimblehq.smsforwarder.R
import co.nimblehq.smsforwarder.databinding.FragmentFilterManagerBinding
import co.nimblehq.smsforwarder.databinding.ViewLoadingBinding
import co.nimblehq.smsforwarder.extension.hideSoftKeyboard
import co.nimblehq.smsforwarder.extension.subscribeOnClick
import co.nimblehq.smsforwarder.extension.toastShort
import co.nimblehq.smsforwarder.extension.visibleOrGone
import co.nimblehq.smsforwarder.lib.IsLoading
import co.nimblehq.smsforwarder.ui.base.BaseFragment
import co.nimblehq.smsforwarder.ui.helpers.handleVisualOverlaps
import co.nimblehq.smsforwarder.ui.screens.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@AndroidEntryPoint
class FilterManagerFragment : BaseFragment<FragmentFilterManagerBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel by viewModels<FilterManagerViewModel>()
    private var isLoading = false
    private lateinit var viewLoadingBinding: ViewLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFilterManagerBinding
        get() = { inflater, container, attachToParent ->
            FragmentFilterManagerBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        showAppBar()

        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)

        with(binding) {
            btFilterSubmit
                .subscribeOnClick(::saveFilter)
                .addToDisposables()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteFilter) {
            // TODO: Implement delete filter
            toastShort("Delete filter")
            navigator.navigateUp()
            return true
        }
        return false
    }

    override fun handleVisualOverlaps() {
        with(binding) {
            tvFilterForwardIcon.handleVisualOverlaps()
        }
    }

    override fun bindViewModel() {
        viewModel.navigator bindTo navigator::navigate
        viewModel.showLoading bindTo ::showLoading
        viewModel.error bindTo ::displayError
    }

    private fun showLoading(isLoading: IsLoading) {
        this.isLoading = isLoading
        viewLoadingBinding.pbLoading.visibleOrGone(isLoading)
    }

    private fun saveFilter() {
        requireActivity().hideSoftKeyboard()

        viewModel.saveFilter(
            sender = binding.etFilterSendFromProviderName.text.toString(),
            smsTemplate = binding.etFilterSendFromTemplate.text.toString(),
            forwardEmail = binding.etFilterForwardToEmail.text.toString(),
            forwardSlackChannel = binding.etFilterForwardToSlack.text.toString(),
        ).subscribeBy(
            onComplete = {
                binding.etFilterSendFromProviderName.text?.clear()
                binding.etFilterSendFromTemplate.text?.clear()
                binding.etFilterForwardToEmail.text?.clear()
                binding.etFilterForwardToSlack.text?.clear()

                toaster.display(R.string.filter_save_success)
            },
            onError = ::displayError
        ).addToDisposables()
    }
}
