package co.nimblehq.rxjava.data.service

import co.nimblehq.rxjava.data.service.response.ExampleResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/top.json?limit=10")
    fun getExampleData(): Single<Response<ExampleResponse>>
}
