package co.nimblehq.smsforwarder.di.modules

import android.content.Context
import co.nimblehq.smsforwarder.IncomingSmsPersistenceImpl
import co.nimblehq.smsforwarder.SmsForwarderApplication
import co.nimblehq.smsforwarder.domain.persistence.IncomingSmsPersistence
import co.nimblehq.smsforwarder.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.smsforwarder.domain.schedulers.SchedulerProvider
import co.nimblehq.smsforwarder.ui.common.Toaster
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @Provides
    fun provideContext(application: SmsForwarderApplication): Context = application

    @Provides
    fun toaster(@ApplicationContext context: Context): Toaster = Toaster(context)

    @Provides
    fun schedulerProvider(): BaseSchedulerProvider = SchedulerProvider()

    @Provides
    @Singleton
    fun provideIncomingSmsObservable(persistence: IncomingSmsPersistenceImpl): IncomingSmsPersistence = persistence
}
