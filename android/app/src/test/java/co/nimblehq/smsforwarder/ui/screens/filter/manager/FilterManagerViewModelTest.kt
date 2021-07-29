package co.nimblehq.smsforwarder.ui.screens.filter.manager

import co.nimblehq.smsforwarder.ui.screens.filter.manager.FilterManagerViewModel
import org.amshove.kluent.mock
import org.junit.Before

class FilterManagerViewModelTest {

    private lateinit var managerViewModel: FilterManagerViewModel

    @Before
    fun setup() {
        managerViewModel = FilterManagerViewModel(
            mock()
        )
    }
}
