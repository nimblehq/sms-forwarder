package co.nimblehq.smsforwarder.domain.repository

import co.nimblehq.smsforwarder.data.database.FiltersDao
import co.nimblehq.smsforwarder.data.database.FilterDto
import co.nimblehq.smsforwarder.domain.data.Filter
import co.nimblehq.smsforwarder.domain.data.toEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface DatabaseRepository {

    fun getAll(): Single<List<Filter>>

    fun replaceAll(filters: List<FilterDto>): Completable

    fun insert(filter: FilterDto): Single<Long>

    fun insertAll(filters: List<FilterDto>): Completable

    fun delete(id: Int): Completable

    fun deleteAll(): Completable
}

class DatabaseRepositoryImpl @Inject constructor(
    private val dao: FiltersDao
) : DatabaseRepository {

    override fun getAll(): Single<List<Filter>> {
        return dao.getAll().map { list -> list.map { it.toEntity() } }
    }

    override fun replaceAll(filters: List<FilterDto>): Completable {
        return Completable.fromCallable {
            dao.replaceAll(filters)
        }
    }

    override fun insert(filter: FilterDto): Single<Long> {
        return dao.insert(filter)
    }

    override fun insertAll(filters: List<FilterDto>): Completable {
        return Completable.fromCallable {
            dao.insertFilters(filters)
        }
    }

    override fun delete(id: Int): Completable {
        return Completable.fromCallable {
            dao.deleteById(id)
        }
    }

    override fun deleteAll(): Completable {
        return Completable.fromCallable {
            dao.deleteAll()
        }
    }
}
