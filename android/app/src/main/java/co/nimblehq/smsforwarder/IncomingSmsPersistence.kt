package co.nimblehq.smsforwarder

import co.nimblehq.smsforwarder.domain.data.IncomingSmsEntity
import co.nimblehq.smsforwarder.domain.persistence.IncomingSmsPersistence
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class IncomingSmsPersistenceImpl @Inject constructor() : IncomingSmsPersistence {

    private val incomingSmsSubject = PublishSubject.create<IncomingSmsEntity>()

    override val incomingSms: Observable<IncomingSmsEntity>
        get() = incomingSmsSubject

    override fun newIncomingSms(incomingSms: IncomingSmsEntity) {
        incomingSmsSubject.onNext(incomingSms)
    }
}
