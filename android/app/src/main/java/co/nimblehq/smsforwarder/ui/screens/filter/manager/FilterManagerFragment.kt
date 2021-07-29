package co.nimblehq.smsforwarder.ui.screens.filter.manager

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import co.nimblehq.smsforwarder.R
import co.nimblehq.smsforwarder.databinding.FragmentFilterManagerBinding
import co.nimblehq.smsforwarder.extension.subscribeOnClick
import co.nimblehq.smsforwarder.extension.toastShort
import co.nimblehq.smsforwarder.ui.base.BaseFragment
import co.nimblehq.smsforwarder.ui.helpers.handleVisualOverlaps
import co.nimblehq.smsforwarder.ui.screens.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilterManagerFragment : BaseFragment<FragmentFilterManagerBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel by viewModels<FilterManagerViewModel>()

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

        with(binding) {
            btFilterSubmit
                .subscribeOnClick(navigator::navigateUp)
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
    }
}
