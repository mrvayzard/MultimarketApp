package com.vayzard.market.domain

interface MarketRepository {
  suspend fun selectMarket(market: Market)
  suspend fun getCurrentMarket(): Market
}