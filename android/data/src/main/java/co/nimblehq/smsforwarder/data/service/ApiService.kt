package co.nimblehq.smsforwarder.data.service

import co.nimblehq.smsforwarder.data.service.request.ForwardRequest
import co.nimblehq.smsforwarder.data.service.response.ForwardResponse
import co.nimblehq.smsforwarder.data.service.response.GetFiltersResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/api/v1/sms/forward")
    fun forward(
        @Body request: ForwardRequest
    ): Single<Response<ForwardResponse>>

    @GET("/api/v1/filter/list")
    fun getFilters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<Response<GetFiltersResponse>>
}
