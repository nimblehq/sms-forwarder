package co.nimblehq.smsforwarder.ui.screens.home

import android.Manifest
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import co.nimblehq.smsforwarder.R
import co.nimblehq.smsforwarder.databinding.FragmentHomeBinding
import co.nimblehq.smsforwarder.databinding.ViewLoadingBinding
import co.nimblehq.smsforwarder.domain.data.Sms
import co.nimblehq.smsforwarder.extension.*
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
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    @Inject
    lateinit var rxPermissions: RxPermissions

    private val viewModel by viewModels<HomeViewModelImpl>()

    private lateinit var smsAdapter: SmsAdapter
    private lateinit var viewLoadingBinding: ViewLoadingBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        showAppBar()

        viewLoadingBinding = ViewLoadingBinding.bind(binding.root)
        setupDataList()

        binding.btHomeAddFilter
            .subscribeOnClick { viewModel.navigateToFilter() }
            .addToDisposables()
    }

    override fun handleVisualOverlaps() {
        with(binding) {
            listOf(
                rvHomeSmsData,
                vHomeBackground,
                btHomeAddFilter
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
        with(binding.rvHomeSmsData) {
            adapter = SmsAdapter().also {
                smsAdapter = it
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
        smsAdapter.items = sms
    }

    private fun showLoading(isLoading: IsLoading) {
        binding.btHomeAddFilter.isEnabled = !isLoading
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
