package co.nimblehq.smsforwarder.ui.screens.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.nimblehq.smsforwarder.databinding.FragmentFilterBinding
import co.nimblehq.smsforwarder.extension.subscribeOnClick
import co.nimblehq.smsforwarder.ui.base.BaseFragment
import co.nimblehq.smsforwarder.ui.helpers.handleVisualOverlaps
import co.nimblehq.smsforwarder.ui.screens.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilterFragment : BaseFragment<FragmentFilterBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel by viewModels<FilterViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFilterBinding
        get() = { inflater, container, attachToParent ->
            FragmentFilterBinding.inflate(inflater, container, attachToParent)
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
