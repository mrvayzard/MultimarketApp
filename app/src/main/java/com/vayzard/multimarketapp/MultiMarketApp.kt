package com.vayzard.multimarketapp

import android.app.Application
import android.content.Context
import com.vayzard.feature.enrollment.di.featureEnrollmentModule
import com.vayzard.feature.enrollment.mxa.di.featureEnrollmentMxaModule
import com.vayzard.market.data.MarketRepositoryImpl
import com.vayzard.market.data.dataStore
import com.vayzard.market.domain.MarketRepository
import com.vayzard.utils.CoroutineDispatcherProvider
import com.vayzard.utils.DefaultCoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

class MultiMarketApp : Application() {
  private val appModule = module {
    factory {
      CoroutineScope(SupervisorJob() + get<CoroutineDispatcherProvider>().default())
    }
    single {
      MarketRepositoryImpl(
        preferences = get(),
        dispatcher = get()
      )
    } bind MarketRepository::class
    single { DefaultCoroutineDispatcherProvider } bind CoroutineDispatcherProvider::class
    single { get<Context>().dataStore }
  }

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@MultiMarketApp)
      modules(
        appModule,
        featureEnrollmentModule,
        featureEnrollmentMxaModule,
      )
    }
  }
}