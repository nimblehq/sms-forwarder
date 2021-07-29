package co.nimblehq.smsforwarder.ui.screens.filter

import android.Manifest
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import co.nimblehq.smsforwarder.R
import co.nimblehq.smsforwarder.databinding.FragmentAllFiltersBinding
import co.nimblehq.smsforwarder.databinding.ViewLoadingBinding
import co.nimblehq.smsforwarder.extension.visibleOrGone
import co.nimblehq.smsforwarder.lib.IsLoading
import co.nimblehq.smsforwarder.ui.base.BaseFragment
import co.nimblehq.smsforwarder.ui.helpers.handleVisualOverlaps
import co.nimblehq.smsforwarder.ui.screens.MainNavigator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AllFiltersFragment : BaseFragment<FragmentAllFiltersBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    @Inject
    lateinit var rxPermissions: RxPermissions

    private val viewModel by viewModels<AllFiltersViewModel>()

    private lateinit var filterAdapter: FilterAdapter
    private lateinit var viewLoadingBinding: ViewLoadingBinding
    private var isLoading = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAllFiltersBinding
        get() = { inflater, container, attachToParent ->
            FragmentAllFiltersBinding.inflate(inflater, container, attachToParent)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setupView() {
        showAppBar()

        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)
        setupDataList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addFilter && !isLoading) {
            viewModel.navigateToFilterManager()
            return true
        }
        return false
    }

    override fun handleVisualOverlaps() {
        with(binding) {
            listOf(
                rvAllFiltersData
            ).forEach { it.handleVisualOverlaps() }
        }
    }

    override fun bindViewModel() {
        viewModel.showLoading bindTo ::showLoading
        viewModel.error bindTo ::displayError
        viewModel.data bindTo ::bindData
        viewModel.navigator bindTo navigator::navigate

        requestReceiveSmsPermission()
    }

    private fun requestReceiveSmsPermission() {
        rxPermissions
            .requestEach(Manifest.permission.RECEIVE_SMS)
            .subscribe(::handleReceiveSmsPermission)
            .addToDisposables()
    }

    private fun handleReceiveSmsPermission(permission: Permission) {
        when {
            permission.granted -> {
                // Granted
            }
            permission.shouldShowRequestPermissionRationale -> {
                // Deny
                toaster.display(R.string.permission_denied)
            }
            else -> {
                // Deny and never ask again
                showPermissionRequiredDialog()
            }
        }
    }

    private fun setupDataList() {
        with(binding.rvAllFiltersData) {
            adapter = FilterAdapter().also {
                filterAdapter = it
            }
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    RecyclerView.VERTICAL
                )
            )
        }
    }

    private fun bindData(filters: List<FilterUiModel>) {
        Timber.d(filters.toString())
        filterAdapter.items = filters
    }

    private fun showLoading(isLoading: IsLoading) {
        this.isLoading = isLoading
        viewLoadingBinding.pbLoading.visibleOrGone(isLoading)
    }

    private fun showPermissionRequiredDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.permission_require_title)
            .setMessage(R.string.permission_receive_sms_require_message)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.permission_require_settings) { _, _ ->
                // TODO Navigate to Settings
                toaster.display("Navigate to Settings")
            }
            .setCancelable(false)
            .show()
    }
}
