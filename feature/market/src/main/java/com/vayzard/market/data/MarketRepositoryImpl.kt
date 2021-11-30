package com.vayzard.market.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.vayzard.market.domain.Market
import com.vayzard.market.domain.MarketRepository
import com.vayzard.utils.coroutines.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class MarketRepositoryImpl(
  private val preferences: DataStore<Preferences>,
  private val dispatcher: CoroutineDispatcherProvider,
) : MarketRepository {
  override suspend fun selectMarket(market: Market) {
    withContext(dispatcher.io()) {
      preferences.edit {
        it[KEY_MARKET_CODE] = getMarketCode(market)
      }
    }
  }

  override suspend fun getCurrentMarket(): Market = withContext(dispatcher.io()) {
    val code = preferences.data
      .firstOrNull()
      ?.get(KEY_MARKET_CODE) ?: -1

    return@withContext getMarketByCode(code)
  }

  private fun getMarketByCode(code: Int): Market {
    return when (code) {
      1 -> Market.US
      2 -> Market.Japan
      3 -> Market.Mexico
      else -> Market.Japan
    }
  }

  private fun getMarketCode(market: Market): Int {
    return when (market) {
      Market.US -> 1
      Market.Japan -> 2
      Market.Mexico -> 3
    }
  }

  companion object {
    private val KEY_MARKET_CODE = intPreferencesKey("KEY_MARKET_CODE")
  }
}