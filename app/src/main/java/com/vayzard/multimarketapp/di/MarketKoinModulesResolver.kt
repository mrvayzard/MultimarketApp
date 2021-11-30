package com.vayzard.multimarketapp.di

import com.vayzard.feature.enrollment.jpn.di.featureEnrollmentJpnModule
import com.vayzard.feature.enrollment.mxa.di.featureEnrollmentMxaModule
import com.vayzard.market.domain.Market
import org.koin.core.module.Module

object MarketKoinModulesResolver {
  fun resolve(market: Market): List<Module> {
    return when (market) {
      Market.Japan -> listOf(featureEnrollmentJpnModule)
      Market.Mexico -> listOf(featureEnrollmentMxaModule)
    }
  }
}
