package co.nimblehq.smsforwarder.domain.persistence

import co.nimblehq.smsforwarder.domain.data.Filter
import io.reactivex.Completable
import io.reactivex.Observable

interface FiltersPersistence {

    val filters: Observable<List<Filter>>

    fun getAll(): Completable

    fun insert(filter: Filter): Completable
}
