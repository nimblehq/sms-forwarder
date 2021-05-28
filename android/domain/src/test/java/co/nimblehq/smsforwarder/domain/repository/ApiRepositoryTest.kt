package co.nimblehq.smsforwarder.domain.repository

import co.nimblehq.smsforwarder.data.service.ApiService
import co.nimblehq.smsforwarder.domain.test.MockUtil
import io.reactivex.Single
import org.amshove.kluent.*
import org.junit.Test
import retrofit2.Response

class ApiRepositoryTest {

    @Test
    fun `When execute forward request successfully, it returns success response`() {
        val mockService = mock<ApiService>()
        When calling mockService.forward(any()) itReturns
                Single.just(Response.success(MockUtil.forwardResponse))
        val repository = ApiRepositoryImpl(mockService)

        val testSubscriber = repository.forward(
            "incomingNumber",
            "messageBody",
            "email"
        ).test()
        testSubscriber
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `When execute forward request failed, it returns wrapped error`() {
        val mockService = mock<ApiService>()
        When calling mockService.forward(any()) itReturns Single.error(Throwable())
        val repository = ApiRepositoryImpl(mockService)

        val testSubscriber = repository.forward(
            "incomingNumber",
            "messageBody",
            "email"
        ).test()
        testSubscriber
            .assertError(Throwable::class.java)
            .assertValueCount(0)
    }
}
