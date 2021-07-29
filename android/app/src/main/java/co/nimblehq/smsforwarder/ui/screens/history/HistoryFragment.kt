package co.nimblehq.smsforwarder.ui.screens.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.nimblehq.smsforwarder.databinding.FragmentHistoryBinding
import co.nimblehq.smsforwarder.ui.base.BaseFragment
import co.nimblehq.smsforwarder.ui.helpers.handleVisualOverlaps
import co.nimblehq.smsforwarder.ui.screens.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel by viewModels<HistoryViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHistoryBinding
        get() = { inflater, container, attachToParent ->
            FragmentHistoryBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        showAppBar()
    }

    override fun handleVisualOverlaps() {
        with(binding) {
            listOf(
                rvHistorySmsData,
                vHistoryBackground
            ).forEach { it.handleVisualOverlaps() }
        }
    }

    override fun bindViewModel() {
        viewModel.navigator bindTo navigator::navigate
    }
}
