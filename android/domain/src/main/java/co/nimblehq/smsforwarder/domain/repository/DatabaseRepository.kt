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

    fun replaceAll(vararg dto: FilterDto): Completable

    fun insert(dto: FilterDto): Single<Long>

    fun insertAll(vararg dto: FilterDto): Completable

    fun delete(id: Int): Completable

    fun deleteAll(): Completable
}

class DatabaseRepositoryImpl @Inject constructor(
    private val dao: FiltersDao
) : DatabaseRepository {

    override fun getAll(): Single<List<Filter>> {
        return dao.getAll().map { list -> list.map { it.toEntity() } }
    }

    override fun replaceAll(vararg dto: FilterDto): Completable {
        return Completable.fromCallable {
            dao.replaceAll(*dto)
        }
    }

    override fun insert(dto: FilterDto): Single<Long> {
        return dao.insert(dto)
    }

    override fun insertAll(vararg dto: FilterDto): Completable {
        return Completable.fromCallable {
            dao.insertAll(*dto)
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
