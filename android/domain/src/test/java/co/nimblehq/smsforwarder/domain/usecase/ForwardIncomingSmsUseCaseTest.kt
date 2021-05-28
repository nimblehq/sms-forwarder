package co.nimblehq.smsforwarder.domain.usecase

import co.nimblehq.smsforwarder.domain.data.error.DataError
import co.nimblehq.smsforwarder.domain.repository.ApiRepository
import co.nimblehq.smsforwarder.domain.schedulers.TrampolineSchedulerProvider
import co.nimblehq.smsforwarder.domain.test.MockUtil
import io.reactivex.Single
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test

class ForwardIncomingSmsUseCaseTest {

    private val mockRepository = mock<ApiRepository>()
    private lateinit var useCase: ForwardIncomingSmsUseCase

    @Before
    fun setup() {
        useCase = ForwardIncomingSmsUseCase(
            TrampolineSchedulerProvider,
            mockRepository
        )
    }

    @Test
    fun `When execute usecase request successfully, it returns positive result`() {
        val data = MockUtil.dataList
        When calling mockRepository.exampleData() itReturns Single.just(data)

        val testSubscriber = useCase.execute(Unit).test()
        testSubscriber
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue(data)
    }

    @Test
    fun `When execute usecase request failed, it returns wrapped error`() {
        When calling mockRepository.exampleData() itReturns Single.error(Throwable())

        val testSubscriber = useCase.execute(Unit).test()
        testSubscriber
            .assertError { it is DataError.GetDataError }
            .assertValueCount(0)
    }
}
