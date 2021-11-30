package com.vayzard.utils.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
  fun main(): CoroutineDispatcher
  fun default(): CoroutineDispatcher
  fun io(): CoroutineDispatcher
  fun unconfined(): CoroutineDispatcher
}
