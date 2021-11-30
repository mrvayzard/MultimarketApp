package com.vayzard.utils

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
  fun main(): CoroutineDispatcher
  fun default(): CoroutineDispatcher
  fun io(): CoroutineDispatcher
  fun unconfined(): CoroutineDispatcher
}
