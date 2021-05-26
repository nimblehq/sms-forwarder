package co.nimblehq.smsforwarder.ui.screens.home

import android.Manifest
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import co.nimblehq.smsforwarder.R
import co.nimblehq.smsforwarder.databinding.FragmentHomeBinding
import co.nimblehq.smsforwarder.databinding.ViewLoadingBinding
import co.nimblehq.smsforwarder.domain.data.Data
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

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var dataAdapter: DataAdapter
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
            .subscribeOnClick { viewModel.input.refresh() }
            .addToDisposables()
    }

    override fun handleVisualOverlaps() {
        with(binding) {
            rvHomeData.handleVisualOverlaps()
            vHomeBackground.handleVisualOverlaps()
            btHomeAddFilter.handleVisualOverlaps()
        }
    }

    override fun bindViewEvents() {
        super.bindViewEvents()
        dataAdapter
            .itemClick
            .subscribeOnItemClick {
                when (it) {
                    is DataAdapter.OnItemClick.Item ->
                        viewModel.input.navigateToDetail(it.data)
                }
            }
            .addToDisposables()
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
        with(binding.rvHomeData) {
            adapter = DataAdapter().also {
                dataAdapter = it
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

    private fun bindData(data: List<Data>) {
        dataAdapter.items = data
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
