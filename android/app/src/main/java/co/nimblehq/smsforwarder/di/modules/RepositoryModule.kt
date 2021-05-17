package co.nimblehq.smsforwarder.di.modules

import co.nimblehq.smsforwarder.data.service.ApiService
import co.nimblehq.smsforwarder.domain.repository.ApiRepository
import co.nimblehq.smsforwarder.domain.repository.ApiRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideApiRepository(
        apiService: ApiService
    ): ApiRepository = ApiRepositoryImpl(apiService)
}
