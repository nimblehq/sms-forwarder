package co.nimblehq.smsforwarder.ui.screens.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.nimblehq.smsforwarder.databinding.FragmentLoginBinding
import co.nimblehq.smsforwarder.extension.subscribeOnClick
import co.nimblehq.smsforwarder.ui.base.BaseFragment
import co.nimblehq.smsforwarder.ui.helpers.handleVisualOverlaps
import co.nimblehq.smsforwarder.ui.screens.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel by viewModels<LoginViewModelImpl>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = { inflater, container, attachToParent ->
            FragmentLoginBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        with(binding) {
            btLogin
                .subscribeOnClick(viewModel::navigateToFilter)
                .addToDisposables()
        }
    }

    override fun handleVisualOverlaps() {
        with(binding) {
            btLogin.handleVisualOverlaps()
        }
    }

    override fun bindViewModel() {
        viewModel.navigator bindTo navigator::navigate
    }
}
