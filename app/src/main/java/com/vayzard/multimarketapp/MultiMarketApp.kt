package com.vayzard.multimarketapp

import android.app.Application
import com.vayzard.feature.enrollment.di.featureEnrollmentModule
import com.vayzard.feature.enrollment.mxa.di.featureEnrollmentMxaModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MultiMarketApp: Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@MultiMarketApp)
      modules(
        featureEnrollmentModule,
        featureEnrollmentMxaModule
      )
    }
  }
}