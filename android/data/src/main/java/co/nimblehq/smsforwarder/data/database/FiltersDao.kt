package co.nimblehq.smsforwarder.data.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FiltersDao {

    @Query("SELECT * FROM $SMS_FILTERING_TABLE_NAME")
    fun getAll(): Single<List<FilterDto>>

    @Transaction
    fun replaceAll(vararg filter: FilterDto) {
        deleteAll()
        insertAll(*filter)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(filter: FilterDto): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg filter: FilterDto)

    @Query("DELETE FROM $SMS_FILTERING_TABLE_NAME WHERE id = :id")
    fun deleteById(id: Int)

    @Query("DELETE FROM $SMS_FILTERING_TABLE_NAME")
    fun deleteAll()
}
