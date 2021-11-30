package com.vayzard.multimarketapp.di

import com.vayzard.feature.enrollment.di.featureEnrollmentModule
import com.vayzard.feature.enrollment.jpn.di.featureEnrollmentJpnModule
import com.vayzard.feature.enrollment.mxa.di.featureEnrollmentMxaModule
import com.vayzard.feature.enrollment.us.di.featureEnrollmentUsModule
import com.vayzard.market.domain.Market
import org.koin.core.module.Module

object MarketKoinModulesResolver {
  fun resolve(market: Market): List<Module> {
    val marketSpecificModules = when (market) {
      Market.US -> listOf(featureEnrollmentUsModule)
      Market.Japan -> listOf(featureEnrollmentJpnModule)
      Market.Mexico -> listOf(featureEnrollmentMxaModule)
    }
    return listOf(featureEnrollmentModule).plus(marketSpecificModules)
  }
}
