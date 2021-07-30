package co.nimblehq.smsforwarder.data.service.interceptor

import android.util.Base64
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

const val HEADER_AUTHORIZATION = "Authorization"

class AppRequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request().newBuilder()

        // TODO: Remove this when using OAuth2.0 authentication
        val basicAuth = "Basic " + String(Base64.encode("admin:aSnL%a@C3W@95kXr".toByteArray(), Base64.NO_WRAP))
        originalRequest.header(HEADER_AUTHORIZATION, basicAuth)

        val afterIntercepted = originalRequest.build()
        return chain.proceed(afterIntercepted)
    }
}
