package co.nimblehq.smsforwarder.data.service

import co.nimblehq.smsforwarder.data.service.request.ForwardRequest
import co.nimblehq.smsforwarder.data.service.response.ForwardResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/v1/sms/forward")
    fun forward(
        @Body request: ForwardRequest
    ): Single<Response<ForwardResponse>>
}
