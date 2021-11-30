package com.vayzard.market.di

import android.content.Context
import com.vayzard.market.data.MarketRepositoryImpl
import com.vayzard.market.data.dataStore
import com.vayzard.market.domain.MarketRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val featureMarketModule = module {
  factory {
    MarketRepositoryImpl(
      preferences = get(),
      dispatcher = get()
    )
  } bind MarketRepository::class

  single { get<Context>().dataStore }
}