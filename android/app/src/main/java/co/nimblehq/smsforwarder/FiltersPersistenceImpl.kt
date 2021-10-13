package co.nimblehq.smsforwarder

import co.nimblehq.smsforwarder.domain.data.Filter
import co.nimblehq.smsforwarder.domain.data.toDto
import co.nimblehq.smsforwarder.domain.persistence.FiltersPersistence
import co.nimblehq.smsforwarder.domain.repository.DatabaseRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class FiltersPersistenceImpl @Inject constructor(
    private val repository: DatabaseRepository
) : FiltersPersistence {

    private val filtersSubject = BehaviorSubject.createDefault<List<Filter>>(emptyList())

    override val filters: Observable<List<Filter>>
        get() = filtersSubject

    override fun getAll(): Completable {
        return repository
            .getAll()
            .flatMapCompletable(this::update)
    }

    override fun insert(filter: Filter): Completable {
        return repository
            .insert(filter.toDto())
            .ignoreElement()
            .andThen(repository.getAll())
            .flatMapCompletable(this::update)
    }

    override fun delete(id: Int): Completable {
        return repository
            .delete(id)
            .andThen(repository.getAll())
            .flatMapCompletable(this::update)
    }

    private fun update(new: List<Filter>): Completable =
        Completable.fromAction {
            filtersSubject.onNext(new)
        }
}
