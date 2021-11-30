package com.vayzard.utils.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object DefaultCoroutineDispatcherProvider : CoroutineDispatcherProvider {
  override fun main(): CoroutineDispatcher = Dispatchers.Main
  override fun default(): CoroutineDispatcher = Dispatchers.Default
  override fun io(): CoroutineDispatcher = Dispatchers.IO
  override fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}
