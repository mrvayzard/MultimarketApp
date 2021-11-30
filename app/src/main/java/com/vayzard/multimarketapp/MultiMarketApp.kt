package com.vayzard.multimarketapp

import android.app.Application
import com.vayzard.feature.enrollment.di.featureEnrollmentModule
import com.vayzard.market.di.featureMarketModule
import com.vayzard.market.domain.MarketRepository
import com.vayzard.multimarketapp.di.MarketKoinModulesResolver
import com.vayzard.utils.CoroutineDispatcherProvider
import com.vayzard.utils.DefaultCoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.loadKoinModules
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

  private val marketRepository: MarketRepository by inject()

  private fun loadCurrentMarketModules() {
    val currentMarket = runBlocking {
      marketRepository.getCurrentMarket()
    }
    val marketModules = MarketKoinModulesResolver.resolve(currentMarket)
    loadKoinModules(marketModules)
  }

  override fun onCreate() {
    super.onCreate()
    startKoin {
      allowOverride(true)
      androidContext(this@MultiMarketApp)
      modules(
        appModule,
        featureEnrollmentModule,
        featureMarketModule,
      )
      loadCurrentMarketModules()
    }
  }
}