package co.nimblehq.smsforwarder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FilterDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filtersDao(): FiltersDao
}