package co.nimblehq.smsforwarder.ui.screens.second

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.nimblehq.smsforwarder.databinding.FragmentFilterBinding
import co.nimblehq.smsforwarder.domain.data.FilterSmsEntity
import co.nimblehq.smsforwarder.domain.persistence.FilteringSmsPersistence
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

    @Inject
    lateinit var filteringSmsPersistence: FilteringSmsPersistence

    private val viewModel by viewModels<FilterViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFilterBinding
        get() = { inflater, container, attachToParent ->
            FragmentFilterBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        with(binding) {
            btFilterSubmit
                .subscribeOnClick(::addNewFilter)
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

    private fun addNewFilter() {
        with(binding) {
            val sender = etFilterSendFromProviderName.text.toString()
            val forwardEmailAddress = etFilterForwardToEmail.text.toString()

            val filterEntity = FilterSmsEntity(
                sender,
                forwardEmailAddress
            )

            filteringSmsPersistence.addFilter(filterEntity)
        }

        navigator.navigateUp()
    }
}
