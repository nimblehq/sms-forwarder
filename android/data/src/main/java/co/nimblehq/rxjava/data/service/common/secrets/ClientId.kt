package co.nimblehq.rxjava.data.service.common.secrets

interface ClientId {
    companion object {
        // TODO: modify for your usage
        const val ID = "client_id"
    }
    val value: String
}
