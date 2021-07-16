package co.nimblehq.smsforwarder.data.service.common.secrets

class ApiEndpointUrlImpl : ApiEndpointUrl {
    override val value: String
        get() = "https://nimble-sms-forwarder.herokuapp.com/"
}
