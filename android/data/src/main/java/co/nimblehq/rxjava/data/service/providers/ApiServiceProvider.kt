package co.nimblehq.smsforwarder.data.service.providers

import co.nimblehq.smsforwarder.data.service.ApiService
import retrofit2.Retrofit

class ApiServiceProvider {
    companion object {
        fun getApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }
}
