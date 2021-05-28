package co.nimblehq.smsforwarder.domain.test

import co.nimblehq.smsforwarder.data.service.response.ForwardResponse
import co.nimblehq.smsforwarder.domain.data.Sms

object MockUtil {

    val forwardResponse: ForwardResponse
        get() = ForwardResponse(
            "message",
            "success",
            "data"
        )

    val sms = listOf(
        Sms("Grab", "Your OTP is 123456", "", ""),
        Sms("Grab", "Your OTP is 112233", "", ""),
        Sms("Grab", "Your OTP is 111222", "", ""),
        Sms("Grab", "Your OTP is 123456", "", ""),
        Sms("Grab", "Your OTP is 123456", "", ""),
        Sms("Grab", "Your OTP is 123456", "", ""),
        Sms("Grab", "Your OTP is 123456", "", "")
    )
}
