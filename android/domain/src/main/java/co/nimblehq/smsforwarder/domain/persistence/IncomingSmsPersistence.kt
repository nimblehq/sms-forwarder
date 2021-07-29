package co.nimblehq.smsforwarder.domain.persistence

import co.nimblehq.smsforwarder.domain.data.IncomingSmsEntity
import io.reactivex.Observable

interface IncomingSmsPersistence {

    val incomingSms: Observable<IncomingSmsEntity>

    fun newIncomingSms(incomingSms: IncomingSmsEntity)
}
