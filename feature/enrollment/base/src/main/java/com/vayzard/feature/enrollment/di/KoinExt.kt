package com.vayzard.feature.enrollment.di

import android.content.ComponentCallbacks
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.ScopeID

fun <E : Enum<E>> ComponentCallbacks.getOrCreateScope(scopeId: ScopeID) =
  getKoin().getOrCreateScope(scopeId, named(scopeId))
