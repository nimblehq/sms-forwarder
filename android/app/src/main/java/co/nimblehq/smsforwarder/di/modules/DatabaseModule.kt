package co.nimblehq.smsforwarder.di.modules

import android.content.Context
import androidx.room.Room
import co.nimblehq.smsforwarder.data.database.AppDatabase
import co.nimblehq.smsforwarder.data.database.FiltersDao
import co.nimblehq.smsforwarder.domain.repository.DatabaseRepository
import co.nimblehq.smsforwarder.domain.repository.DatabaseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "sms-forwarder.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFiltersDao(database: AppDatabase): FiltersDao =
        database.filtersDao()

    @Provides
    @Singleton
    fun provideDatabaseRepository(dao: FiltersDao): DatabaseRepository =
        DatabaseRepositoryImpl(dao)
}
