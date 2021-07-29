package co.nimblehq.smsforwarder.ui.screens.filter.manager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.nimblehq.smsforwarder.databinding.FragmentFilterManagerBinding
import co.nimblehq.smsforwarder.extension.subscribeOnClick
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

    override fun handleVisualOverlaps() {
        with(binding) {
            tvFilterForwardIcon.handleVisualOverlaps()
        }
    }

    override fun bindViewModel() {
        viewModel.navigator bindTo navigator::navigate
    }
}
