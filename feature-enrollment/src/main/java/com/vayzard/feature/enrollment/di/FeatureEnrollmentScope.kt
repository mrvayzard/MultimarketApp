package com.vayzard.feature.enrollment.di

import org.koin.core.Koin
import org.koin.core.qualifier.named
import org.koin.core.scope.ScopeID

object FeatureEnrollmentScope {
  const val ID: ScopeID = "enrollment"

  fun getOrCreate(koin: Koin) {
    koin.getOrCreateScope(ID, named(ID))
  }

  fun close(koin: Koin) {
    koin.getOrCreateScope(ID, named(ID)).close()
  }
}