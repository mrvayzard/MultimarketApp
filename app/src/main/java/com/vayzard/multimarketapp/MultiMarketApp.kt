package com.vayzard.multimarketapp

import android.app.Application
import com.vayzard.feature.enrollment.di.featureEnrollmentModule
import com.vayzard.feature.enrollment.mxa.di.featureEnrollmentMxaModule
import com.vayzard.market.di.featureMarketModule
import com.vayzard.utils.coroutines.CoroutineDispatcherProvider
import com.vayzard.utils.coroutines.DefaultCoroutineDispatcherProvider
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
      DefaultCoroutineDispatcherProvider
    } bind CoroutineDispatcherProvider::class
  }

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@MultiMarketApp)
      modules(
        appModule,
        featureEnrollmentModule,
        featureEnrollmentMxaModule,
        featureMarketModule,
      )
    }
  }
}