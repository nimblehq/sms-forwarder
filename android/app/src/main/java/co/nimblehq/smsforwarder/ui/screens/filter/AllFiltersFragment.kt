package co.nimblehq.smsforwarder.ui.screens.filter

import android.Manifest
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import co.nimblehq.smsforwarder.R
import co.nimblehq.smsforwarder.databinding.FragmentAllFiltersBinding
import co.nimblehq.smsforwarder.databinding.ViewLoadingBinding
import co.nimblehq.smsforwarder.domain.data.Sms
import co.nimblehq.smsforwarder.extension.subscribeOnClick
import co.nimblehq.smsforwarder.extension.visibleOrGone
import co.nimblehq.smsforwarder.lib.IsLoading
import co.nimblehq.smsforwarder.ui.base.BaseFragment
import co.nimblehq.smsforwarder.ui.helpers.handleVisualOverlaps
import co.nimblehq.smsforwarder.ui.screens.MainNavigator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllFiltersFragment : BaseFragment<FragmentAllFiltersBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    @Inject
    lateinit var rxPermissions: RxPermissions

    private val viewModel by viewModels<AllFiltersViewModelImpl>()

    private lateinit var filterAdapter: FilterAdapter
    private lateinit var viewLoadingBinding: ViewLoadingBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAllFiltersBinding
        get() = { inflater, container, attachToParent ->
            FragmentAllFiltersBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        showAppBar()

        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)
        setupDataList()

        binding.btAllFiltersAdd
            .subscribeOnClick { viewModel.navigateToFilter() }
            .addToDisposables()
    }

    override fun handleVisualOverlaps() {
        with(binding) {
            listOf(
                rvAllFiltersData,
                vAllFiltersBackground,
                btAllFiltersAdd
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
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    RecyclerView.VERTICAL
                )
            )
        }
    }

    private fun bindData(sms: List<Sms>) {
        filterAdapter.items = sms
    }

    private fun showLoading(isLoading: IsLoading) {
        binding.btAllFiltersAdd.isEnabled = !isLoading
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
