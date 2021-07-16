package co.nimblehq.smsforwarder.domain.test

import co.nimblehq.smsforwarder.data.service.response.ForwardResponse

object MockUtil {

    val forwardResponse: ForwardResponse
        get() = ForwardResponse(
            "message",
            "success",
            "data"
        )
}
