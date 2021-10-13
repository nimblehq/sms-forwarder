package co.nimblehq.smsforwarder.ui.screens.filter.manager

import org.amshove.kluent.mock
import org.junit.Before

class FilterManagerViewModelTest {

    private lateinit var managerViewModel: FilterManagerViewModel

    @Before
    fun setup() {
        managerViewModel = FilterManagerViewModel(mock())
    }
}
