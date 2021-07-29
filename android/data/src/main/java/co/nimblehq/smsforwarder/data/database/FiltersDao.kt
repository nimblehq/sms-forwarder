package co.nimblehq.smsforwarder.data.database

import androidx.room.*
import io.reactivex.Single

@Dao
interface FiltersDao {

    @Query("SELECT * FROM $SMS_FILTERING_TABLE_NAME")
    fun getAll(): Single<List<FilterDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(filter: FilterDto): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg filter: FilterDto)

    @Delete
    fun delete(filter: FilterDto)
}