package com.vayzard.multimarketapp

import android.app.Application
import com.vayzard.feature.enrollment.di.featureEnrollmentModule
import com.vayzard.feature.enrollment.mxa.di.featureEnrollmentMxaModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MultiMarketApp : Application() {
  private val appModule = module {
    factory {
      CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
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